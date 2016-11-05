package com.vac.vmusic.homefragment.childfragment.localmusicfragment.view;

import android.content.Context;

import com.vac.vmusic.views.MyScrollView;

/**
 * Created by vac on 16/10/22.
 *
 */
public interface ILocalMusicFragment  {
    MyScrollView getMyScrollView();
    Context getMyContext();
    void showNativeMusicCount(int num);
}
