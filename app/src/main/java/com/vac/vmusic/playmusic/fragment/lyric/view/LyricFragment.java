package com.vac.vmusic.playmusic.fragment.lyric.view;

import android.view.View;
import android.widget.ListView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.playmusic.fragment.lyric.presenter.LyricFragmentPresenter;
import com.vac.vmusic.service.binder.MusicBinder;

/**
 * Created by vac on 16/11/1.
 *
 */
public class LyricFragment extends BaseSwipeBackFragment implements ILyricFragment{
    private ListView listView;
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

        MusicBinder musicBinder = getMusicIbinder();
        TingSong tingSong = musicBinder.getCurrentSong();

        LyricFragmentPresenter lyricFragmentPresenter = new LyricFragmentPresenter(this);
        lyricFragmentPresenter.checkLocalLyric(tingSong);
    }

}
