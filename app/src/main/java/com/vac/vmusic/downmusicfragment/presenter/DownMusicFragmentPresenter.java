package com.vac.vmusic.downmusicfragment.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.downmusicfragment.adapter.LocalMusicAdapter;
import com.vac.vmusic.downmusicfragment.model.DownMusicFragmentModel;
import com.vac.vmusic.downmusicfragment.view.IDownMusicFragment;
import com.vac.vmusic.views.DividerItemDecoration;

import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragmentPresenter implements OnLocalMusicLoadListener {
    private IDownMusicFragment iDownMusicFragment;
    private DownMusicFragmentModel downMusicFragmentModel;
    private LocalMusicAdapter localMusicAdapter;
    public DownMusicFragmentPresenter(IDownMusicFragment iDownMusicFragment_){
        this.iDownMusicFragment = iDownMusicFragment_;
        downMusicFragmentModel = new DownMusicFragmentModel();
    }

    public void loadData(){
        localMusicAdapter = new LocalMusicAdapter(iDownMusicFragment.getMyContext()
                ,iDownMusicFragment.getListener());
        RecyclerView recyclerView = iDownMusicFragment.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iDownMusicFragment.getMyContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(iDownMusicFragment.getMyContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(localMusicAdapter);
        downMusicFragmentModel.getDownloadMusic(this);
    }

    @Override
    public void onLocalMusicLoadListener(List<LocalMusic> localMusics) {
        for (LocalMusic localMusic:localMusics){
            Log.i("tag",localMusic.toString());
        }

        View headView = LayoutInflater.from(iDownMusicFragment.getMyContext()).inflate(R.layout.head_play_music,iDownMusicFragment.getRecyclerView(),false);
        localMusicAdapter.setData(localMusics);
        localMusicAdapter.setHeadView(headView);


    }

    public List<LocalMusic> getLocalMusic(){
        return localMusicAdapter.getData();
    }
}