package com.vac.vmusic.homefragment.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vac.vmusic.base.BaseSwipeBackFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/1.
 *
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseSwipeBackFragment> mData = new ArrayList<>();
    public CustomFragmentAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    public void setData(List<BaseSwipeBackFragment> swipeBackFragments){
        if (swipeBackFragments!=null){
            mData.clear();
            mData.addAll(swipeBackFragments);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
