package com.vac.vmusic.homefragment.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import rx.Observable;

/**
 * Created by vac on 16/10/21.
 *
 */
public interface IHomeFragment {
    TabLayout getTabLayout();
    ViewPager getViewPager();
    FragmentManager getMyFm();
    Observable<Integer> getScrollYObservable();

    RelativeLayout getTopContent();
}
