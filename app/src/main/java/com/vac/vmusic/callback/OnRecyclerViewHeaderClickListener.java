package com.vac.vmusic.callback;

import com.vac.vmusic.beans.search.TingSong;

import java.util.List;

/**
 * Created by vac on 16/11/15.
 *
 */
public interface OnRecyclerViewHeaderClickListener {
    public static final int TYPE_RANDOM = 1;
    public static final int TYPE_ORDER = 2;
    void onMusicPlay(List<? extends TingSong> tingSongList,int position,int type);
    void onMusicManager(List<? extends TingSong> tingSongList);
}
