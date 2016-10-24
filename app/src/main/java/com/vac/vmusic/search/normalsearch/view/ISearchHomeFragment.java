package com.vac.vmusic.search.normalsearch.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

/**
 * Created by vac on 16/10/24.
 *
 */
public interface ISearchHomeFragment {
    TabLayout getTabLayout();
    ViewPager getViewPager();
    FragmentManager getFm();
}
