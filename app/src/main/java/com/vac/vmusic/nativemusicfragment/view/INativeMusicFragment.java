package com.vac.vmusic.nativemusicfragment.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.vac.vmusic.callback.OnItemClickListener;

/**
 * Created by vac on 16/11/5.
 *
 */
public interface INativeMusicFragment {

    Context getMyContext();

    OnItemClickListener getListener();

    RecyclerView getRecyclerView();
}
