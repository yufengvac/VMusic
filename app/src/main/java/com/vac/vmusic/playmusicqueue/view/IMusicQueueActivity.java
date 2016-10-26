package com.vac.vmusic.playmusicqueue.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.vac.vmusic.playmusicqueue.adapter.MusicQueueAdapter;
import com.vac.vmusic.service.PlayService;

/**
 * Created by vac on 16/10/25.
 *
 */
public interface IMusicQueueActivity {
    void close();
    PlayService.MusicBinder getMusicBinder();
    RecyclerView getRecyclerView();
    Context getContext();
    MusicQueueAdapter getAdapter();
}
