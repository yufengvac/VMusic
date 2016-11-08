package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view;

import android.view.View;

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
}
