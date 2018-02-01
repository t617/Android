package com.example.zhiqiang.vq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity {
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
        super.onCreate(savedInstanceState);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        String mVideoPath = getIntent().getStringExtra("stream_addr");
        String mDdescription = getIntent().getStringExtra("description");
        String mPortrait = getIntent().getStringExtra("portrait");
        String isLiving = getIntent().getStringExtra("type");
        if (isLiving != null && isLiving.equals("living")) {
            JZVideoPlayer.SAVE_PROGRESS = false;
        } else {
            JZVideoPlayer.SAVE_PROGRESS = true;
        }
        jzVideoPlayerStandard.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, mDdescription);
        Picasso.with(this).load(mPortrait).into(jzVideoPlayerStandard.thumbImageView);
        jzVideoPlayerStandard.startVideo();
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
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
