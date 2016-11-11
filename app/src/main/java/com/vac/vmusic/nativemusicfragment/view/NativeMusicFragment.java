package com.vac.vmusic.nativemusicfragment.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.nativemusicfragment.presenter.NativeMusicPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.HomeColorManager;

/**
 * Created by vac on 16/11/5.
 *
 */
public class NativeMusicFragment extends BaseSwipeBackFragment implements INativeMusicFragment,OnItemClickListener,View.OnClickListener,OnPlayMusicStateListener{
    private RecyclerView recyclerView;
    private NativeMusicPresenter nativeMusicPresenter;
    public static NativeMusicFragment getNativeMusicFragment(Bundle bundle){
        NativeMusicFragment nativeMusicFragment = new NativeMusicFragment();
        if (bundle!=null){
            nativeMusicFragment.setArguments(bundle);
        }
        return nativeMusicFragment;
    }

    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.native_music_fragment;
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.native_music_fragment_recycle_view);
        nativeMusicPresenter = new NativeMusicPresenter(this);
        nativeMusicPresenter.loadData();
        LinearLayout topContent =(LinearLayout) view.findViewById(R.id.native_music_fragment_top_content);
        topContent.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        ImageView backImageView = (ImageView) view.findViewById(R.id.native_music_top_back_imageview);
        backImageView.setOnClickListener(this);
        getMusicIbinder().registerOnPlayMusicStateListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMusicIbinder().unRegisterOnPlayMusicStateListener(this);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public Context getMyContext() {
        return getActivity();
    }

    @Override
    public OnItemClickListener getListener() {
        return this;
    }

    @Override
    public MusicBinder getMyMusicBinder() {
        return getMusicIbinder();
    }

    @Override
    public void onItemClick(View view, int position) {
        getMusicIbinder().setMusicPlayList(nativeMusicPresenter.getLocalMusic(),true);
        getMusicIbinder().beginToPlay(position,nativeMusicPresenter.getLocalMusic().get(position));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.native_music_top_back_imageview:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onMusicPlayed(TingSong music) {
        nativeMusicPresenter.getLocalMusicAdapter().setFlagInPosition(false,
                getMyMusicBinder().getCurrentPlayingPosition(),music);
    }

    @Override
    public void onMusicPaused(TingSong music) {
        nativeMusicPresenter.getLocalMusicAdapter().setFlagInPosition(true,
                getMyMusicBinder().getCurrentPlayingPosition(),music);
    }

    @Override
    public void onMusicStopped() {

    }

    @Override
    public void onPlayModeChanged(int playMode) {

    }

    @Override
    public void onNewSongPlayed(TingSong music, int position) {
        nativeMusicPresenter.getLocalMusicAdapter().setFlagInPosition(false,position,music);
    }

    @Override
    public void onPlayProgressUpdate(int percent, long currentTime) {

    }
}
