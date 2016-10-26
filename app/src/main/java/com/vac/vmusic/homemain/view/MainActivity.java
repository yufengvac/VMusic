package com.vac.vmusic.homemain.view;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.homemain.presenter.MainActivityPresenter;
import com.vac.vmusic.playmusicqueue.view.MusicQueueActivity;
import com.vac.vmusic.service.PlayService;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.MyMenuButton;
import com.vac.vmusic.views.MyPauseButton;
import com.vac.vmusic.views.MyProgressbar;
import com.vac.vmusic.views.MyTriangle;

import rx.Observable;

public class MainActivity extends BaseActivity implements IMainActivity , OnPlayMusicStateListener ,View.OnClickListener{

    private Observable<AddFragment> addFragmentObservable;
    private TextView songNameView,singerNameView;
    private MyProgressbar myProgressbar;
    private MyTriangle myTriangle;
    private MyPauseButton myPauseButton;
    private MyMenuButton myMenuButton;
    private PlayService.MusicBinder musicBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            setMusicIbinder(iBinder);
            musicBinder = (PlayService.MusicBinder) iBinder;
            musicBinder.registerOnPlayMusicStateListener(MainActivity.this);
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

        myTriangle = (MyTriangle)findViewById(R.id.main_play_mytriangle);
        myPauseButton = (MyPauseButton) findViewById(R.id.main_play_pause_mypausebtn);
        myMenuButton = (MyMenuButton) findViewById(R.id.main_menu_mymenubtn);
        HomeColorManager homeColorManager = new HomeColorManager();
        myTriangle.setColor(homeColorManager.getCurrentColor());
        myPauseButton.setColor(homeColorManager.getCurrentColor());
        myMenuButton.setColor(homeColorManager.getCurrentColor());
        myProgressbar.setProgressColor(homeColorManager.getCurrentColor());

        addFragmentObservable = RxBus.get().register("addFragment", AddFragment.class);
        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.initContent();
    }

    @Override
    public void initService() {
        startService(new Intent(this, PlayService.class));
        bindService(new Intent(this, PlayService.class),mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public Observable<AddFragment> getAddFragmentObservable() {
        return addFragmentObservable;
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


    public void playSong(View view){
        musicBinder.togglePlay();
    }

    public void openPlayingSongQueue(View view){
        startActivity(new Intent(MainActivity.this, MusicQueueActivity.class));
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_top_out);
    }

    @Override
    public void onMusicPlayed(TingSong music) {
        if (music!=null){
            songNameView.setText(music.getName());
            singerNameView.setText(music.getSingerName());
        }
        myTriangle.setVisibility(View.GONE);
        myPauseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMusicPaused(TingSong music) {
        myTriangle.setVisibility(View.VISIBLE);
        myPauseButton.setVisibility(View.GONE);
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
        myProgressbar.setProgress(0);
    }

    @Override
    public void onPlayProgressUpdate(int percent) {
        myProgressbar.setProgress(percent);
    }
}
