package com.vac.vmusic.search.normalsearch.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.search.normalsearch.presenter.SearchHomeFragmentPresenter;

/**
 * Created by vac on 16/10/24.
 *
 */
public class SearchHomeFragment extends BaseSwipeBackFragment implements ISearchHomeFragment{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle bundle;

    public static SearchHomeFragment getSearchHomeFragment(Bundle bundle){
        SearchHomeFragment searchHomeFragment = new SearchHomeFragment();
        if (bundle!=null){
            searchHomeFragment.setArguments(bundle);
        }
        return searchHomeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
           bundle = getArguments();
        }
    }

    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.search_home_fragment;
    }

    @Override
    public void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.search_home_fragment_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.search_home_fragment_view_pager);

        SearchHomeFragmentPresenter searchHomeFragmentPresenter = new SearchHomeFragmentPresenter(this,bundle);
        searchHomeFragmentPresenter.loadSearchFragment();
    }

    @Override
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public FragmentManager getFm() {
        return getChildFragmentManager();
    }
}
