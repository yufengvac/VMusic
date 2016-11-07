package com.vac.vmusic.playmusicqueue.view;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.playmusicqueue.adapter.MusicQueueAdapter;
import com.vac.vmusic.playmusicqueue.presenter.MusicQueueActivityPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.ViewUtil;

/**
 * Created by vac on 16/10/25.
 *
 */
public class MusicQueueActivity extends BaseActivity implements IMusicQueueActivity,OnPlayMusicStateListener,OnItemClickListener{
    private  RecyclerView recyclerView;
    private ImageView modeImageView;
    private TextView modeTextView;
    private MusicQueueAdapter musicQueueAdapter;
    private int lastPosition = -1;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.music_queue_activity;
    }

    @Override
    public void initView() {
        ActionBar actionBar = getActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        recyclerView = (RecyclerView) findViewById(R.id.music_queue_activity_recycler_view);
        modeImageView = (ImageView) findViewById(R.id.music_queue_activity_mode_image_view);
        modeTextView = (TextView) findViewById(R.id.music_queue_activity_mode_text_view);

        musicQueueAdapter = new MusicQueueAdapter(this,this);

        MusicQueueActivityPresenter musicQueueActivityPresenter = new MusicQueueActivityPresenter(this);
        musicQueueActivityPresenter.loadData();
        getMusicBinder().registerOnPlayMusicStateListener(this);

        int position = getMusicBinder().getCurrentPlayingPosition();
        if (position>=0){
            musicQueueAdapter.setFocuse(lastPosition,getMusicBinder().getCurrentPlayingPosition());
            lastPosition = getMusicBinder().getCurrentPlayingPosition();
            recyclerView.smoothScrollToPosition(position);
            onPlayModeChanged(getMusicBinder().getPlayMode());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMusicBinder().unRegisterOnPlayMusicStateListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        view.setPadding(0, 0, 0, ViewUtil.getNavigationBarHeight(this));
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.x = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int)(getResources().getDisplayMetrics().heightPixels*0.65);

        getWindowManager().updateViewLayout(view, lp);

    }

    @Override
    public void close(){
        finish();
        overridePendingTransition(R.anim.push_bottom_out, 0);
    }

    public void backClose(View view){
        close();
    }

    @Override
    public MusicBinder getMusicBinder() {
        return getMusicIbinder();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public MusicQueueAdapter getAdapter() {
        return musicQueueAdapter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            close();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onMusicPlayed(TingSong music) {

    }

    @Override
    public void onMusicPaused(TingSong music) {

    }

    @Override
    public void onMusicStopped() {

    }

    @Override
    public void onPlayModeChanged(int playMode) {
        int size = getMusicBinder().getMusicPlayList().size();
        String modeStr="";
        switch (playMode){
            case PlayService.PlayMode.REPEAT_SINGLE:
                modeImageView.setImageResource(R.drawable.icon_mode_repete_one);
                modeStr = getString(R.string.mode_repeat_single)+"("+size+"首)";
                break;
            case PlayService.PlayMode.REPEAT:
                modeImageView.setImageResource(R.drawable.icon_mode_all_repeat);
                modeStr = getString(R.string.mode_repeat)+"("+size+"首)";
                break;
            case PlayService.PlayMode.SEQUENTIAL:
                modeImageView.setImageResource(R.drawable.icon_mode_order);
                modeStr = getString(R.string.mode_sequential)+"("+size+"首)";
                break;
            case PlayService.PlayMode.SHUFFLE:
                modeImageView.setImageResource(R.drawable.icon_mode_shuffle);
                modeStr = getString(R.string.mode_shuffle)+"("+size+"首)";
                break;
        }
        modeTextView.setText(modeStr);
    }

    public void changeMode(View view){
        getMusicBinder().changePlayMode();
    }

    @Override
    public void onNewSongPlayed(TingSong music, int position) {
        musicQueueAdapter.setFocuse(lastPosition,position);
        lastPosition = position;
    }

    @Override
    public void onPlayProgressUpdate(int percent,long currentTime) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (view instanceof LinearLayout){
            getMusicBinder().beginToPlay(position,musicQueueAdapter.getData().get(position));
        }else if (view instanceof ImageView){
            if (view.getTag().equals("favor")){
                musicQueueAdapter.favorSong(position);
            }else if (view.getTag().equals("remove")){

            }
        }
    }
}
