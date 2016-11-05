package com.vac.vmusic.nativemusicfragment.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.downmusicfragment.adapter.LocalMusicAdapter;
import com.vac.vmusic.nativemusicfragment.model.NativeMusicModel;
import com.vac.vmusic.nativemusicfragment.view.INativeMusicFragment;
import com.vac.vmusic.views.DividerItemDecoration;

import java.util.List;

/**
 * Created by vac on 16/11/5.
 *
 */
public class NativeMusicPresenter implements OnLocalMusicLoadListener{
    private INativeMusicFragment iNativeMusicFragment;
    private NativeMusicModel nativeMusicModel;
    private LocalMusicAdapter localMusicAdapter;
    private List<LocalMusic> localMusicList;
    public NativeMusicPresenter(INativeMusicFragment iNativeMusicFragment_){
        this.iNativeMusicFragment = iNativeMusicFragment_;
        nativeMusicModel = new NativeMusicModel();
    }

    public void loadData(){
        RecyclerView recyclerView = iNativeMusicFragment.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iNativeMusicFragment.getMyContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(iNativeMusicFragment.getMyContext(),LinearLayoutManager.VERTICAL));
        localMusicAdapter = new LocalMusicAdapter(iNativeMusicFragment.getMyContext()
                ,iNativeMusicFragment.getListener());
        recyclerView.setAdapter(localMusicAdapter);
        nativeMusicModel.loadData(iNativeMusicFragment.getMyContext(),this);
    }

    @Override
    public void onLocalMusicLoadListener(List<LocalMusic> localMusics) {
        localMusicList = localMusics;
        for (LocalMusic localMusic:localMusics){
            Log.i("TAG",localMusic.toString());
        }

        View headView = LayoutInflater.from(iNativeMusicFragment.getMyContext()).inflate(R.layout.head_play_music,iNativeMusicFragment.getRecyclerView(),false);

        localMusicAdapter.setData(localMusics);
        localMusicAdapter.setHeadView(headView);
    }

    public List<LocalMusic> getLocalMusic(){
        return localMusicList;
    }
}
