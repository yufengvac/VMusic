package com.vac.vmusic.downmusic.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.downmusic.adapter.LocalMusicAdapter;
import com.vac.vmusic.downmusic.model.DownMusicFragmentModel;
import com.vac.vmusic.downmusic.view.IDownMusicFragment;
import com.vac.vmusic.views.DividerItemDecoration;

import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragmentPresenter implements DownMusicFragmentModel.OnLocalMusicLoadListener{
    private IDownMusicFragment iDownMusicFragment;
    private DownMusicFragmentModel downMusicFragmentModel;
    private LocalMusicAdapter localMusicAdapter;
    public DownMusicFragmentPresenter(IDownMusicFragment iDownMusicFragment_){
        this.iDownMusicFragment = iDownMusicFragment_;
        downMusicFragmentModel = new DownMusicFragmentModel();
    }

    public void loadData(){
        downMusicFragmentModel.getDownloadMusic(this);
    }

    @Override
    public void onLocalMusicLoadListener(List<LocalMusic> localMusics) {
        for (LocalMusic localMusic:localMusics){
            Log.i("tag",localMusic.toString());
        }
        localMusicAdapter = new LocalMusicAdapter(iDownMusicFragment.getMyContext()
                ,iDownMusicFragment.getListener());
        RecyclerView recyclerView = iDownMusicFragment.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iDownMusicFragment.getMyContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(iDownMusicFragment.getMyContext(),LinearLayoutManager.VERTICAL));
        iDownMusicFragment.getRecyclerView().setAdapter(localMusicAdapter);
        localMusicAdapter.setData(localMusics);
    }

    public List<LocalMusic> getLocalMusic(){
        return localMusicAdapter.getData();
    }
}
