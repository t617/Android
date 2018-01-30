package com.example.zhiqiang.vq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity {
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        String mVideoPath = getIntent().getStringExtra("stream_addr");
//        String mVideoPath = "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8";
        String mDdescrription = getIntent().getStringExtra("description");
        String mPortrait = getIntent().getStringExtra("portrait");
        jzVideoPlayerStandard.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, mDdescrription);
        Picasso.with(this).load(mPortrait).into(jzVideoPlayerStandard.thumbImageView);
        jzVideoPlayerStandard.startVideo();
        super.onCreate(savedInstanceState);
//        String videoPath = "http://pull99.inke.cn/live/1517272651530567.flv?ikDnsOp=1&ikHost=ws&ikOp=1&codecInfo=8192&dpSrcG=-1";
//        String mVideoPath = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            Log.e("bp", "bp");
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.goOnPlayOnPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }
}
