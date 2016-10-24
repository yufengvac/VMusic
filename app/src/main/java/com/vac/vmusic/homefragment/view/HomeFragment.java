package com.vac.vmusic.homefragment.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.homefragment.presenter.HomeFragmentPresenter;
import com.vac.vmusic.search.view.SearchMainFragment;
import com.vac.vmusic.utils.RxBus;

import rx.Observable;

/**
 * Created by vac on 16/10/21.
 * HoomeFragment
 */
public class HomeFragment extends BaseSwipeBackFragment implements View.OnClickListener,IHomeFragment{
    private TabLayout tabLayout;
    private ImageButton openMenuView,openSearchView;
    private ViewPager viewPager;

    private Observable<Integer> scrollYObservable;

    private RelativeLayout topContent;
    private View statusView;

    public static HomeFragment getHomeFragment(Bundle bundle){
        HomeFragment fragment = new HomeFragment();
        if (bundle!=null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.home_fragment;
    }

    @Override
    public void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.main_tab_layout);
        openMenuView = (ImageButton) view.findViewById(R.id.main_tab_menu_imageview);
        openSearchView = (ImageButton) view.findViewById(R.id.main_tab_search_imageview);
        openMenuView.setOnClickListener(this);
        openSearchView.setOnClickListener(this);

        viewPager = (ViewPager) view.findViewById(R.id.home_fragment_viewpager);

        topContent = (RelativeLayout) view.findViewById(R.id.home_fragment_top_content);
        statusView = view.findViewById(R.id.home_fragment_status_bar);


        scrollYObservable = RxBus.get().register("scrollY",Integer.class);
        HomeFragmentPresenter homeFragmentPresenter = new HomeFragmentPresenter(this);
        homeFragmentPresenter.addFragmentToViewPager();


    }

    @Override
    public Observable<Integer> getScrollYObservable() {
        return scrollYObservable;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("scrollY",scrollYObservable);
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
    public FragmentManager getMyFm() {
        return getChildFragmentManager();
    }

    @Override
    public RelativeLayout getTopContent() {
        return topContent;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.main_tab_menu_imageview){

        }else if (id == R.id.main_tab_search_imageview){
            AddFragment addFragment = new AddFragment();
            addFragment.setFromFragment(HomeFragment.this);
            addFragment.setToFragment(SearchMainFragment.getSearchMainFragment(null));
            RxBus.get().post("addFragment",addFragment);
        }
    }
}
