package com.vac.vmusic.mvdetail.view;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.views.MyMediaController;
import com.vac.vmusic.views.VideoPlayController;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by vac on 16/11/9.
 *
 */
public class MvDetailActivity extends BaseActivity implements View.OnTouchListener{
    private VideoView videoView;
    private String url;
    private VideoPlayController mPlayController;
    private LinearLayout bgLineatLayout;
    private float mDownX;
    private float mDownY;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.mv_detail_activity;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        videoView = (VideoView) findViewById(R.id.mv_detail_activity_video_view);
        bgLineatLayout = (LinearLayout) findViewById(R.id.mv_detail_activity_bg);
//        videoView.setMediaController(new MyMediaController(this));
        mPlayController = new VideoPlayController(this, videoView, bgLineatLayout);
        videoView.setVideoPath(url);
        videoView.setOnTouchListener(this);
        videoView.requestFocus();
        videoView.setBufferSize(512);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayController.onDestroy();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 保持屏幕比例正确
        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 2);
        mPlayController.hide();
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        long sec = outState.getLong("time");
        videoView.seekTo(sec);
        super.onRestoreInstanceState(outState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        long sec = videoView.getCurrentPosition();
        outState.putLong("time", sec);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mDownX - event.getX()) > 50 || Math.abs(mDownY - event.getY()) > 50) {
                    // 移动超过一定距离，ACTION_UP取消这次事件
                    mDownX = Integer.MAX_VALUE;
                    mDownY = Integer.MAX_VALUE;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayController != null && Math.abs(mDownX - event.getX()) <= 50 && Math
                        .abs(mDownY - event.getY()) <= 50) {
                    // 解决与背景点击事件的冲突
                    if (mPlayController.isShowing()) {
                        mPlayController.hide();
                    } else {
                        mPlayController.show();
                    }
                }
                break;
        }
        return true;
    }
}
