package com.vac.vmusic.playmusic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.downloadmanager.dbcontrol.FileHelper;
import com.vac.vmusic.playmusic.presenter.PlayMusicActivityPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.AlphaUtil;
import com.vac.vmusic.utils.Constants;
import com.vac.vmusic.utils.FileUtil;
import com.vac.vmusic.utils.TimeUtil;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/25.
 *
 */
@SuppressLint("NewApi")
public class PlayMusicActivity extends BaseActivity implements View.OnClickListener,IPlayMusicActivity,OnPlayMusicStateListener,SeekBar.OnSeekBarChangeListener {
    private ImageView backImageView,preImageView,nextImageView,playOrPauseImageView,modeImageView;
    private ImageView downloadImageView;
    private TextView songNameTextView;
    private MusicBinder musicBinder;
    private TextView currentTimeTextView,totalTimeTextView;
    private SeekBar progressSeekbar;
    private PlayMusicActivityPresenter playMusicActivityPresenter;

    private ImageView imageView1,imageView2;
    private Drawable bg1,bg2;

    private Subscription bgSubscription;

    private String hasLoadedSingerName;

    private boolean isSeeking =false;
    private int progressChanged ;

    private HashMap<String,Drawable> drawableHashMap = new HashMap<>();

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
        progressSeekbar.setOnSeekBarChangeListener(this);

        downloadImageView = (ImageView) findViewById(R.id.play_music_activity_download_image_view);
        downloadImageView.setOnClickListener(this);

        imageView1 = (ImageView)findViewById(R.id.play_music_activity_bg1);
        imageView2 = (ImageView)findViewById(R.id.play_music_activity_bg2);
        bg1 = imageView1.getBackground().mutate();
        bg2 = imageView2.getBackground().mutate();
        bg1.setAlpha(255);
        bg2.setAlpha(255);
        bg1.setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);
        bg2.setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);

        musicBinder = getMusicIbinder();
        playMusicActivityPresenter = new PlayMusicActivityPresenter(this);
        initMusicInfo(musicBinder.getCurrentSong());

        musicBinder.registerOnPlayMusicStateListener(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.play_music_activity_view_pager);
        playMusicActivityPresenter.loadViewPager(getSupportFragmentManager(),viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicBinder.unRegisterOnPlayMusicStateListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bgSubscription!=null){
            bgSubscription.unsubscribe();
            bgSubscription = null;
        }

        imageView1.setBackgroundResource(R.drawable.default_music);
        imageView2.setBackgroundResource(R.drawable.default_music);
        bg1.setAlpha(255);
        bg2.setAlpha(255);
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
        playMusicActivityPresenter.loadArtistPic(tingSong.getSingerName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playMusicActivityPresenter.loadArtistPic(musicBinder.getCurrentSong().getSingerName());
    }

    @Override
    public Context getPlayMusicContext() {
        return this;
    }

    @Override
    public void showArtistPic(String singerName) {
        hasLoadedSingerName = singerName;
        final String[] pics = FileUtil.getArtistByName(singerName);
        Log.i("TAG","singerName="+singerName);
        final String rootPath = Constants.ROOT_PATH+Constants.CHILD_ARTIST_PIC+File.separator+singerName+File.separator;
        if (pics!=null&&pics.length>1){
            final Drawable drawable = Drawable.createFromPath(rootPath+pics[0]);
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            imageView2.setBackground(drawable);
            if (bgSubscription!=null&&(!bgSubscription.isUnsubscribed())){
                bgSubscription.unsubscribe();
                bgSubscription = null;
            }
            bgSubscription = Observable.interval(15,15, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .compose(this.<Long>bindToLifecycle()).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    int count = Integer.parseInt(aLong+"");
                    if (bg2.getAlpha()<=20){
                        if (drawableHashMap.get(rootPath+pics[count%pics.length])==null){
                            Drawable drawable = Drawable.createFromPath(rootPath+pics[count%pics.length]);
                            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                            drawableHashMap.put(rootPath+pics[count%pics.length],drawable);
                        }
                        bg2 = drawableHashMap.get(rootPath+pics[count%pics.length]);
                        imageView2.setBackground(bg2);

                        bg1.setAlpha(255);
                        bg2.setAlpha(0);
                        AlphaUtil alphaUtil = new AlphaUtil(bg1,bg2);
                        alphaUtil.toExecute();
                    }else {
                        if (drawableHashMap.get(rootPath+pics[count%pics.length])==null){
                            Drawable drawable = Drawable.createFromPath(rootPath+pics[count%pics.length]);
                            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                            drawableHashMap.put(rootPath+pics[count%pics.length],drawable);
                        }
                        bg1 = drawableHashMap.get(rootPath+pics[count%pics.length]);
                        imageView1.setBackground(bg1);
                        bg1.setAlpha(0);
                        bg2.setAlpha(255);
                        AlphaUtil alphaUtil = new AlphaUtil(bg2,bg1);
                        alphaUtil.toExecute();

                    }
                }
            });
        }
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
            case R.id.play_music_activity_download_image_view:
                TingSong tingSong = musicBinder.getCurrentSong();
                String taskID = tingSong.getSongId()+"";
                if (tingSong.getAuditionList()!=null&&tingSong.getAuditionList().size()>0){
                    TingAudition tingAudition = tingSong.getAuditionList().get(tingSong.getAuditionList().size()-1);
                    String downUrl = tingAudition.getUrl();
                    String fileName = tingSong.getName()+ "." +tingAudition.getSuffix();
                    int state = downLoadManager.addTask(taskID, downUrl,fileName, FileHelper.getFileDefaultPath()+fileName);
                    downLoadManager.setAllTaskListener(this);
                    if (state==-1){
                        Snackbar.make(view,"文件已存在!",Snackbar.LENGTH_LONG).show();
                    }else if (state==1){
                        Snackbar.make(view,"正在下载!",Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(view,"无法下载!",Snackbar.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void onMusicPlayed(TingSong music) {
        playOrPauseImageView.setImageResource(R.drawable.icon_play_pause);
        if (!hasLoadedSingerName.equals(music.getSingerName())){
            if (bgSubscription!=null){
                bgSubscription.unsubscribe();
                bgSubscription = null;
            }
            playMusicActivityPresenter.loadArtistPic(music.getSingerName());
        }

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
        if (!isSeeking){
            progressSeekbar.setProgress(percent);
        }
        currentTimeTextView.setText(TimeUtil.formatTime(currentTime));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        progressChanged = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeeking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        double percent = progressChanged*1.0/300;
        int duration = musicBinder.getCurrentSong().getAuditionList().get(0).getDuration();
        musicBinder.seekToSpecifiedPosition((int)(duration*percent));
        isSeeking = false;
    }
}

