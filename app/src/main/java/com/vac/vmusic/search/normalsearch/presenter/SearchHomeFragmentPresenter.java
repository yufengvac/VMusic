package com.vac.vmusic.search.normalsearch.presenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.homefragment.adapters.MyFragmentAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.view.SearchTabFragment;
import com.vac.vmusic.search.normalsearch.view.ISearchHomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/24.
 *
 */
public class SearchHomeFragmentPresenter {
    private ISearchHomeFragment iSearchHomeFragment;
    private Bundle bundle;
    public SearchHomeFragmentPresenter(ISearchHomeFragment iSearchHomeFragment_,Bundle bundle_){
        this.iSearchHomeFragment = iSearchHomeFragment_;
        this.bundle = bundle_;
    }
    public void loadSearchFragment(){
        TabLayout tabLayout = iSearchHomeFragment.getTabLayout();
        ViewPager viewPager = iSearchHomeFragment.getViewPager();
        String[] strings = new String[]{"单曲","专辑","歌单","MV"};


        List<BaseSwipeBackFragment> baseFragmentList = new ArrayList<>();
        BaseSwipeBackFragment homeFragment1 = SearchTabFragment.newInstance(SearchTabFragment.HomeFragmentType.SINGLE,bundle);
        BaseSwipeBackFragment homeFragment2 = SearchTabFragment.newInstance(SearchTabFragment.HomeFragmentType.ALBUM,bundle);
        BaseSwipeBackFragment homeFragment3 = SearchTabFragment.newInstance(SearchTabFragment.HomeFragmentType.SONGLIST,bundle);
        BaseSwipeBackFragment homeFragment4 = SearchTabFragment.newInstance(SearchTabFragment.HomeFragmentType.MV,bundle);
        baseFragmentList.add(homeFragment1);
        baseFragmentList.add(homeFragment2);
        baseFragmentList.add(homeFragment3);
        baseFragmentList.add(homeFragment4);
        if (viewPager.getAdapter()==null){
            MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(iSearchHomeFragment.getFm(),strings);
            myFragmentAdapter.setData(baseFragmentList);
            viewPager.setAdapter(myFragmentAdapter);
        }
        tabLayout.setupWithViewPager(viewPager);
    }
}
