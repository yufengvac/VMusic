package com.vac.vmusic.playmusicqueue.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.playmusicqueue.adapter.MusicQueueAdapter;
import com.vac.vmusic.playmusicqueue.view.IMusicQueueActivity;
import com.vac.vmusic.views.DividerItemDecoration;
import com.vac.vmusic.views.ListDividerItemDecoration;

import java.util.List;

/**
 * Created by vac on 16/10/25.
 *
 */
public class MusicQueueActivityPresenter {
    private IMusicQueueActivity iMusicQueueActivity;
    public MusicQueueActivityPresenter(IMusicQueueActivity iMusicQueueActivity_){
        this.iMusicQueueActivity = iMusicQueueActivity_;
    }
    public void loadData(){
        List<TingSong> tingSongList = iMusicQueueActivity.getMusicBinder().getMusicPlayList();
        if (tingSongList!=null&&tingSongList.size()>0){
            RecyclerView recyclerView = iMusicQueueActivity.getRecyclerView();
            MusicQueueAdapter musicQueueAdapter = iMusicQueueActivity.getAdapter();
            recyclerView.addItemDecoration(new ListDividerItemDecoration(iMusicQueueActivity.getContext(), DividerItemDecoration.VERTICAL_LIST, R.drawable.icon_line));
//            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            });
            recyclerView.setLayoutManager(new LinearLayoutManager(iMusicQueueActivity.getContext()));
            recyclerView.setAdapter(musicQueueAdapter);
            musicQueueAdapter.setData(iMusicQueueActivity.getMusicBinder().getMusicPlayList());
        }
    }
}
