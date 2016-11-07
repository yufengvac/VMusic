package com.vac.vmusic.skinfragment.activity.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

/**
 * Created by vac on 16/11/7.
 *
 */
public interface ISkinActivity {
    Context getMyContext();
    RadioGroup getRadioGroup();
    ViewPager getViewPager();
    FragmentManager getFm();
}
