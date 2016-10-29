package com.vac.vmusic.playmusic.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.playmusic.presenter.PlayMusicActivityPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.TimeUtil;

/**
 * Created by vac on 16/10/25.
 *
 */
public class PlayMusicActivity extends BaseActivity implements View.OnClickListener,IPlayMusicActivity,OnPlayMusicStateListener{
    private ImageView backImageView,preImageView,nextImageView,playOrPauseImageView,modeImageView;
    private TextView songNameTextView;
    private MusicBinder musicBinder;
    private TextView currentTimeTextView,totalTimeTextView;
    private SeekBar progressSeekbar;
    private PlayMusicActivityPresenter playMusicActivityPresenter;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.play_music_activity;
    }

    @Override
    public void initView() {
        backImageView = (ImageView) findViewById(R.id.play_music_activity_back_image);
        backImageView.setOnClickListener(this);
        preImageView = (ImageView) findViewById(R.id.play_music_activity_pre_image_view);
        preImageView.setOnClickListener(this);
        playOrPauseImageView = (ImageView) findViewById(R.id.play_music_activity_play_or_pause_image_view);
        playOrPauseImageView.setOnClickListener(this);
        nextImageView = (ImageView) findViewById(R.id.play_music_activity_next_image_view);
        nextImageView.setOnClickListener(this);
        modeImageView = (ImageView) findViewById(R.id.play_music_activity_mode_image_view);

        songNameTextView = (TextView) findViewById(R.id.play_music_activity_song_name);

        currentTimeTextView = (TextView) findViewById(R.id.play_music_activity_current_time_text_view);
        totalTimeTextView = (TextView) findViewById(R.id.play_music_activity_total_time_text_view);
        progressSeekbar = (SeekBar) findViewById(R.id.play_music_activity_progress_seek_bar);

        musicBinder = getMusicIbinder();
        playMusicActivityPresenter = new PlayMusicActivityPresenter(this);
        initMusicInfo(musicBinder.getCurrentSong());

        musicBinder.registerOnPlayMusicStateListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicBinder.unRegisterOnPlayMusicStateListener(this);
    }

    @Override
    public void initMusicInfo(TingSong tingSong) {
        songNameTextView.setText(tingSong.getName());
        totalTimeTextView.setText(TimeUtil.formatTime(tingSong.getAuditionList().get(0).getDuration()));
        int state = musicBinder.getCurrentState();
        if (state!= PlayService.PlayState.Playing){
            playOrPauseImageView.setImageResource(R.drawable.icon_play_play);
        }else {
            playOrPauseImageView.setImageResource(R.drawable.icon_play_pause);
        }
    }

    @Override
    public Context getPlayMusicContext() {
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void back(){
        finish();
        overridePendingTransition(R.anim.push_bottom_out,R.anim.push_bottom_out);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.play_music_activity_back_image:
                back();
                break;
            case R.id.play_music_activity_pre_image_view:
                musicBinder.playPre();
                break;
            case R.id.play_music_activity_next_image_view:
                musicBinder.playNext();
                break;
            case R.id.play_music_activity_play_or_pause_image_view:
                musicBinder.togglePlay();
                break;
        }
    }

    @Override
    public void onMusicPlayed(TingSong music) {
        playOrPauseImageView.setImageResource(R.drawable.icon_play_pause);
        playMusicActivityPresenter.loadArtistPic(music.getSingerName());
    }

    @Override
    public void onMusicPaused(TingSong music) {
        playOrPauseImageView.setImageResource(R.drawable.icon_play_play);
    }

    @Override
    public void onMusicStopped() {
        playOrPauseImageView.setImageResource(R.drawable.icon_play_play);
    }

    @Override
    public void onPlayModeChanged(int playMode) {
        switch (playMode){
            case PlayService.PlayMode.REPEAT:
                break;
            case PlayService.PlayMode.REPEAT_SINGLE:
                break;
            case PlayService.PlayMode.SEQUENTIAL:
                break;
            case PlayService.PlayMode.SHUFFLE:
                break;
        }
    }

    @Override
    public void onNewSongPlayed(TingSong music, int position) {
        progressSeekbar.setProgress(0);
        currentTimeTextView.setText(TimeUtil.formatTime(0));
        if (music.getAuditionList()!=null&&music.getAuditionList().size()>0){
            totalTimeTextView.setText(TimeUtil.formatTime(music.getAuditionList().get(0).getDuration()));
        }
        songNameTextView.setText(music.getName());

    }

    @Override
    public void onPlayProgressUpdate(int percent,long currentTime) {
        progressSeekbar.setProgress(percent);
        currentTimeTextView.setText(TimeUtil.formatTime(currentTime));
    }
}
