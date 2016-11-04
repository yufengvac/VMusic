package com.vac.vmusic.playmusic.fragment.lyric.view;

import android.view.View;
import android.widget.ListView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.playmusic.fragment.adapter.LyricAdapter;
import com.vac.vmusic.playmusic.fragment.lyric.presenter.LyricFragmentPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by vac on 16/11/1.
 *
 */
public class LyricFragment extends BaseSwipeBackFragment implements ILyricFragment,OnPlayMusicStateListener {
    private ListView listView;
    private TingSong initTingSong = null;
    private MusicBinder musicBinder;
    private LyricFragmentPresenter lyricFragmentPresenter;
    private Subscription lyricIndexSubsription;
    private Subscription lyricLoadedSubsription;
    private LyricAdapter lyricAdapter;
    public static LyricFragment getLyricFragment(){
        LyricFragment lyricFragment = new LyricFragment();
        return lyricFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.lyric_fragment;
    }

    @Override
    public void initView(View view) {
        listView =(ListView) view.findViewById(R.id.lyric_fragment_list_view);

        musicBinder = getMusicIbinder();
        initTingSong = musicBinder.getCurrentSong();

        lyricFragmentPresenter = new LyricFragmentPresenter(this,this);
        lyricFragmentPresenter.checkLocalLyric(initTingSong);
        musicBinder.registerOnPlayMusicStateListener(this);

        lyricAdapter = new LyricAdapter(getContext());
        lyricAdapter.setLyric(musicBinder.getLyricSentenceList());
        listView.setAdapter(lyricAdapter);

        lyricIndexSubsription = RxBus.get().register("index",Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer index) {
                lyricAdapter.setCurrentSentenceIndex(index);
                listView.smoothScrollToPositionFromTop(index,listView.getHeight() / 2, 700);
            }
        });

        lyricLoadedSubsription = RxBus.get().register("lyricLoaded",Boolean.class).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    lyricAdapter.setLyric(musicBinder.getLyricSentenceList());
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicBinder.unRegisterOnPlayMusicStateListener(this);
        lyricIndexSubsription.unsubscribe();
        lyricLoadedSubsription.unsubscribe();
    }

    @Override
    public void onMusicPlayed(TingSong music) {
//        if (initTingSong.getSongId()!=music.getSongId()){
            lyricFragmentPresenter.checkLocalLyric(music);
//        }
    }

    @Override
    public void onMusicPaused(TingSong music) {

    }

    @Override
    public void onMusicStopped() {

    }

    @Override
    public void onPlayModeChanged(int playMode) {

    }

    @Override
    public void onNewSongPlayed(TingSong music, int position) {
        lyricAdapter.setLyric(null);
    }

    @Override
    public void onPlayProgressUpdate(int percent, long currentTime) {

    }

    @Override
    public void loadLyric(String lyricStr) {
        musicBinder.setLyricContent(lyricStr);
    }

}
