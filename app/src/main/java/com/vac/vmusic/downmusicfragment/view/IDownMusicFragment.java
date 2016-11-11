package com.vac.vmusic.downmusicfragment.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.service.binder.MusicBinder;

/**
 * Created by vac on 16/11/3.
 *
 */
public interface IDownMusicFragment {

    Context getMyContext();

    OnItemClickListener getListener();

    RecyclerView getRecyclerView();

    MusicBinder getMyMusicBinder();


}
