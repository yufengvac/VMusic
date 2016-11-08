package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.vac.vmusic.beans.discover.DiscoverColumn;
import com.vac.vmusic.beans.discover.DiscoverColumnData;

import java.util.List;

import rx.Observable;

/**
 * Created by vac on 16/10/22.
 *
 */
public interface IOnLineMusicFragment {
    View getTopView();
    Observable<Integer> getColorObservable();

    void showBanner(String url,String linkUrl);

    void showChannel(List<DiscoverColumnData> channelList);

    void showEveryOneListener(List<DiscoverColumnData> listenerList);

    GridView getHotSongListGridView();

    GridView getPhoneGridView();

    Context getMyContext();

    void showRecommend(List<DiscoverColumnData> recommendList);
}
