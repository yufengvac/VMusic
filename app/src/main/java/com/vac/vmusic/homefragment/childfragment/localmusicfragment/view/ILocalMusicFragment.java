package com.vac.vmusic.homefragment.childfragment.localmusicfragment.view;

import android.content.Context;

import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.views.BounceScrollView;
import com.vac.vmusic.views.DampView;
import com.vac.vmusic.views.MyScrollView;

import java.util.List;

/**
 * Created by vac on 16/10/22.
 *
 */
public interface ILocalMusicFragment  {
    BounceScrollView getMyScrollView();
    Context getMyContext();
    void showNativeMusicCount(int num);
    void initColor(boolean isInit);
    void changeSkin(String url);

    void refreshMySonglist(List<SongListDetail> songListDetailList);
}
