package com.vac.vmusic.songlistdetail.view;

import android.support.v4.app.FragmentManager;

import com.vac.vmusic.beans.detail.AlbumDetail;

/**
 * Created by vac on 16/11/12.
 *
 */
public interface ISongListDetailFragment {
    void showInfo(AlbumDetail tingAlbum);
    FragmentManager getFm();
}
