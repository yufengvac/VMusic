package com.vac.vmusic.homemain.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.downloadmanager.SQLDownLoadInfo;
import com.vac.vmusic.homemain.presenter.MainActivityPresenter;
import com.vac.vmusic.playmusic.view.PlayMusicActivity;
import com.vac.vmusic.playmusicqueue.view.MusicQueueActivity;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.GlideCircleTransform;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.MyMenuButton;
import com.vac.vmusic.views.MyPauseButton;
import com.vac.vmusic.views.MyProgressbar;
import com.vac.vmusic.views.MyTriangle;

import java.util.List;

import rx.Observable;
@SuppressWarnings("NewApi")
public class MainActivity extends BaseActivity implements IMainActivity , OnPlayMusicStateListener ,View.OnClickListener{

    private Observable<AddFragment> addFragmentObservable;
    private TextView songNameView,singerNameView;
    private MyProgressbar myProgressbar;
    private MyTriangle myTriangle;
    private MyPauseButton myPauseButton;
    private MyMenuButton myMenuButton;
    private MusicBinder musicBinder;
    private ImageView singerLogo;
    private MainActivityPresenter mainActivityPresenter;

    private Animation operatingAnim;
    private ObjectAnimator animator;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            setMusicIbinder(iBinder);
            musicBinder = (MusicBinder) iBinder;
            musicBinder.registerOnPlayMusicStateListener(MainActivity.this);
            mainActivityPresenter.loadLastMusicList();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            getMusicIbinder().unRegisterOnPlayMusicStateListener(MainActivity.this);
            clearMusicIbinder();

        }
    };
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.activity_main;
    }

    @Override
    public void initView() {
        initService();

        songNameView = (TextView)findViewById(R.id.main_song_name);
        singerNameView = (TextView)findViewById(R.id.main_song_artist);
        myProgressbar  = (MyProgressbar) findViewById(R.id.main_progressbar);
        singerLogo = (ImageView) findViewById(R.id.main_singer_logo);

        myTriangle = (MyTriangle)findViewById(R.id.main_play_mytriangle);
        myPauseButton = (MyPauseButton) findViewById(R.id.main_play_pause_mypausebtn);
        myMenuButton = (MyMenuButton) findViewById(R.id.main_menu_mymenubtn);
        HomeColorManager homeColorManager = new HomeColorManager();
        myTriangle.setColor(homeColorManager.getCurrentColor());
        myPauseButton.setColor(homeColorManager.getCurrentColor());
        myMenuButton.setColor(homeColorManager.getCurrentColor());
        myProgressbar.setProgressColor(homeColorManager.getCurrentColor());

        addFragmentObservable = RxBus.get().register("addFragment", AddFragment.class);
        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.initContent();

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        animator = ObjectAnimator.ofFloat(singerLogo, "rotation", 0f, 360.0f);
        animator.setDuration(8000);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void initService() {
        startService(new Intent(this, PlayService.class));
        bindService(new Intent(this, PlayService.class),mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public Observable<AddFragment> getAddFragmentObservable() {
        return addFragmentObservable;
    }

    @Override
    public void initMusicList(List<TingSong> tingSongs) {
        for (TingSong tingSong:tingSongs){
            Log.i("TAG",tingSong.toString());
        }
        getMusicIbinder().setMusicPlayList(tingSongs,false);

        mainActivityPresenter.initFirstWidgetPlayBottom();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("addFragment",addFragmentObservable);
        unbindService(mServiceConnection);
    }

    @Override
    public void addHomeFragment(Fragment fromFragment, Fragment toFragment) {
        addFragment(getSupportFragmentManager(),R.id.main_content,fromFragment,toFragment);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void showSingerPic(String url) {
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(this)).into(singerLogo);
    }

    @Override
    public MusicBinder getMusicBinderFromMain() {
        return getMusicIbinder();
    }

    @Override
    public void initPlayingBottom(TingSong tingSong) {
        if (tingSong!=null){
            songNameView.setText(tingSong.getName());
            singerNameView.setText(tingSong.getSingerName());
            mainActivityPresenter.loadArtistPic(tingSong.getSingerName());
        }
    }

    public void playSong(View view){
        musicBinder.togglePlay();
    }
    public void toPlayMusicActivity(View view){
        startActivity(new Intent(this, PlayMusicActivity.class));
        overridePendingTransition(R.anim.push_bottom_in,R.anim.push_bottom_out);
    }

    public void openPlayingSongQueue(View view){
        startActivity(new Intent(MainActivity.this, MusicQueueActivity.class));
        overridePendingTransition(R.anim.push_bottom_in, R.anim.no_anim);
    }

    @Override
    public void onMusicPlayed(TingSong music) {
       initPlayingBottom(music);
        myTriangle.setVisibility(View.GONE);
        myPauseButton.setVisibility(View.VISIBLE);

//        if (operatingAnim!=null){
//            singerLogo.startAnimation(operatingAnim);
//        }
        if (animator.isStarted()){
            if (animator.isPaused()){
                animator.resume();
            }
        }else {
            animator.start();
        }
    }

    @Override
    public void onMusicPaused(TingSong music) {
        myTriangle.setVisibility(View.VISIBLE);
        myPauseButton.setVisibility(View.GONE);

        if (animator.isRunning()){
            animator.pause();
        }
    }

    @Override
    public void onMusicStopped() {
        myProgressbar.setProgress(0);
        myTriangle.setVisibility(View.VISIBLE);
        myPauseButton.setVisibility(View.GONE);
    }

    @Override
    public void onPlayModeChanged(int playMode) {

    }

    @Override
    public void onNewSongPlayed(TingSong music, int position) {
        initPlayingBottom(music);
        myProgressbar.setProgress(0);
//        if (operatingAnim!=null&&!operatingAnim.hasStarted()){
//            singerLogo.startAnimation(operatingAnim);
//        }
        animator.start();

    }

    @Override
    public void onPlayProgressUpdate(int percent,long currentTime) {
        myProgressbar.setProgress(percent);
    }


}
