package com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.presenter.AlbumSongsFragmentPresenter;

/**
 * Created by vac on 16/11/14.
 *
 */
public class AlbumSongsFragment extends BaseSwipeBackFragment implements IAlbumSongsFragment{


    public static AlbumSongsFragment getAlbumSongsFragment(Bundle bundle){
        AlbumSongsFragment albumSongsFragment = new AlbumSongsFragment();
        if (bundle!=null){
            albumSongsFragment.setArguments(bundle);
        }
        return albumSongsFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.album_songs_fragment;
    }

    @Override
    public void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_songs_fragment_recycle_view);
        AlbumSongsFragmentPresenter albumSongsFragmentPresenter = new AlbumSongsFragmentPresenter(this);
        albumSongsFragmentPresenter.loadData(getArguments(),recyclerView,getActivity());
    }

    @Override
    public MusicBinder getMyMusicBinder() {
        return getMusicIbinder();
    }
}
