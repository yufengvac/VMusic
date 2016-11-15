package com.vac.vmusic.downmusicfragment.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.callback.OnRecyclerViewHeaderClickListener;
import com.vac.vmusic.downmusicfragment.adapter.LocalMusicAdapter;
import com.vac.vmusic.downmusicfragment.model.DownMusicFragmentModel;
import com.vac.vmusic.downmusicfragment.view.IDownMusicFragment;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.views.DividerItemDecoration;

import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragmentPresenter implements OnLocalMusicLoadListener,OnRecyclerViewHeaderClickListener{
    private IDownMusicFragment iDownMusicFragment;
    private DownMusicFragmentModel downMusicFragmentModel;
    private LocalMusicAdapter localMusicAdapter;
    public DownMusicFragmentPresenter(IDownMusicFragment iDownMusicFragment_){
        this.iDownMusicFragment = iDownMusicFragment_;
        downMusicFragmentModel = new DownMusicFragmentModel();
    }

    public void loadData(){
        localMusicAdapter = new LocalMusicAdapter(iDownMusicFragment.getMyContext()
                ,iDownMusicFragment.getListener(),this);
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
        localMusicAdapter.setFlagInPosition(iDownMusicFragment.getMyMusicBinder().getCurrentState()!= PlayService.PlayState.Playing,
                iDownMusicFragment.getMyMusicBinder().getCurrentPlayingPosition(),iDownMusicFragment.getMyMusicBinder().getCurrentSong());
    }

    public LocalMusicAdapter getLocalMusicAdapter(){
        return localMusicAdapter;
    }

    public List<LocalMusic> getLocalMusic(){
        return localMusicAdapter.getData();
    }

    @Override
    public void onMusicPlay(List<? extends TingSong> tingSongList, int position, int type) {
        iDownMusicFragment.getMyMusicBinder().setMusicPlayList(tingSongList,true);
        iDownMusicFragment.getMyMusicBinder().beginToPlay(position,tingSongList.get(position));
    }

    @Override
    public void onMusicManager(List<? extends TingSong> tingSongList) {

    }
}

