package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view;

import android.view.View;

import rx.Observable;

/**
 * Created by vac on 16/10/22.
 *
 */
public interface IOnLineMusicFragment {
    View getTopView();
    Observable<Integer> getColorObservable();
}
