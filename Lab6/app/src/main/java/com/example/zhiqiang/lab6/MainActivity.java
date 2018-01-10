package com.example.zhiqiang.lab6;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static boolean hasPermission = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private boolean playAndPauseFlag = true;
    private boolean endFlag = true;
    private ImageView imageView;
    private ObjectAnimator rotationAnimator = null;
    private SeekBar seekBar;
    private SimpleDateFormat time;
    private IBinder mBinder;
    private MyThread mThread;
    private Button playButton,stopButton, quitButton;
    private TextView playTime, totalTime, playStatus;
    /*建立与service的连接*/
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("service", "connected");
            mBinder = service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            sc = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, sc, Context.BIND_AUTO_CREATE);
        time = new SimpleDateFormat("mm:ss");
        initialObjects();
        buttonListener();
    }
    /*初始化一些控件*/
    public void initialObjects() {
        imageView = (ImageView) findViewById(R.id.cover);
        playTime = (TextView) findViewById(R.id.playTime);
        totalTime = (TextView) findViewById(R.id.totalTime);
        playStatus = (TextView) findViewById(R.id.playStatus);
        playButton = (Button) findViewById(R.id.playAndPause);
        stopButton = (Button) findViewById(R.id.stop);
        quitButton = (Button) findViewById(R.id.quit);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    public void buttonListener() {
        /*播放与暂停*/
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = transactToService(101);
                seekBar.setMax(max);
                totalTime.setText(time.format(max));//设置播放文件的进度最大值
                if (endFlag) {
                    startImageAnimator();
                    startPlaying();
                    endFlag = false;
                } else {
                    if (playAndPauseFlag) {
                        startPlaying();
                        resumeImageAnimator();
                    } else {
                        playButton.setText("PLAY");
                        playStatus.setText("Pausing");
                        pauseImageAnimator();
                        playAndPauseFlag = true;
                    }
                }
            }
        });
        /*停止播放*/
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactToService(102);
                mHandler.obtainMessage(-1).sendToTarget();
                if (!endFlag) {
                    playStatus.setText("Stopped");
                    stopImageAnimator();
                    endFlag = true;
                }
            }
        });
        /*退出*/
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactToService(103);
                unbindService(sc);
                sc = null;
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*播放音乐时候要做的的一些变化*/
    public void startPlaying() {
        playButton.setText("PAUSE");
        playStatus.setText("Playing");
        mThread = new MyThread();
        mThread.start();//start线程
        mHandler.obtainMessage(123).sendToTarget();
        playAndPauseFlag = false;
    }
    /*进程间通信，获取service返回数据*/
    public int transactToService(int code) {
        try {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);
            return reply.readInt();//返回service的进度
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /*Animator动画*/
    public void startImageAnimator() {
        rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        rotationAnimator.setDuration(10000);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        /* 设置为线性，旋转完一圈不会有出现停顿 */
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.start();
    }
    //恢复暂停的时候的状态
    public void resumeImageAnimator() {
        rotationAnimator.resume();
    }

    public void pauseImageAnimator() {
        rotationAnimator.pause();
    }

    public void stopImageAnimator() {
        rotationAnimator.end();
        imageView.clearAnimation();
    }
    /*动态获取读取手机存储权限*/
    public  static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                hasPermission = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*处理不同消息类型*/
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    removeCallbacks(mThread);
                    break;
                case 123:
                    playTime.setText(time.format(transactToService(104)));
                    int progress = transactToService(104);
                    seekBar.setProgress(progress);
                    seekBarUI();
            }
        }
    };
    /*定义线程，100毫秒后更新UI*/
    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sc != null && hasPermission == true) {
                    mHandler.obtainMessage(123).sendToTarget();
                }
            }
        }
    }
    /*进度条与service同步*/
    public void seekBarUI() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    try {
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        data.writeInt(seekBar.getProgress());
                        mBinder.transact(105, data, reply, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
