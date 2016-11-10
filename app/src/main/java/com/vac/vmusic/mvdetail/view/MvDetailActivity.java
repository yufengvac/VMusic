package com.vac.vmusic.mvdetail.view;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.utils.ViewUtil;
import com.vac.vmusic.views.MyMediaController;
import com.vac.vmusic.views.VideoPlayController;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by vac on 16/11/9.
 *
 */
public class MvDetailActivity extends BaseActivity implements MediaController.OnLandscapeListener{
    private VideoView videoView;
    private String url;
    private CenterLayout centerLayout;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.mv_detail_activity;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        videoView = (VideoView) findViewById(R.id.mv_detail_activity_video_view);
        centerLayout = (CenterLayout) findViewById(R.id.mv_detail_activity_center_layout);
        videoView.setMediaController(new MyMediaController(this,this));
        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.setBufferSize(512);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
                videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (ViewUtil.isScreenChange(this)){//横屏
            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            centerLayout.setLayoutParams(layoutParams);
        }else {
            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewUtil.dpToPx(this,85));
            centerLayout.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onLandscape() {
        ViewUtil.rotateScreen(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (ViewUtil.isScreenChange(this)){
                ViewUtil.rotateScreen(this);
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
