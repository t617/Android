package com.example.zhiqiang.lab6;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;

/**
 * Created by zhiqiang on 2017/11/16.
 */

public class MusicService extends Service {
    public static MediaPlayer mp = new MediaPlayer();
    public MyBinder mBinder = new MyBinder();

    public MusicService() {
        Log.e("service", "melt.mp3");
        initialMediaPlayer();
    }
    @Override
    public MyBinder onBind(Intent intent) {
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override// 被关闭之前回调该方法
    public void onDestroy() {
        super.onDestroy();
    }
    //初始化播放器
    public void initialMediaPlayer() {
        try {
            /*手机里面SD卡的文件路径*/
            String path = Environment.getExternalStorageDirectory() + "/qqmusic/melt.mp3";
            mp.setDataSource(path);
            Log.e("service", "initialMediaPlayer");
            mp.prepare();
            mp.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //播放与暂停
    public void playAndPauseMediaPlayer() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
            Log.e("service", "palyAndPause");
        }
    }
    //停止播放
    public void stopMediaPlayer() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("service", "stopMediaPlayer");
        }
    }
    //自定义Binder，
    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code)
            {
                case 101://播放与暂停
                    playAndPauseMediaPlayer();
                    int duration = mp.getDuration();//播放最大进度
                    reply.writeInt(duration);
                    Log.e("binder", "101");
                    break;
                case 102://停止播放
                    stopMediaPlayer();
                    Log.e("binder", "102");
                    break;
                case 103://退出播放
                    mp.stop();
                    mp.release();
                    Log.e("binder", "103");
                    break;
                case 104://获取实时进度
                    int currentPosition = mp.getCurrentPosition();//播放当前进度
                    reply.writeInt(currentPosition);
                    Log.e("binder", "104");
                    break;
                case 105://获取最大进度
                    int position = data.readInt();
                    mp.seekTo(position);//与Activity拖动的进度条同步
                    Log.e("binder", "105");
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}

