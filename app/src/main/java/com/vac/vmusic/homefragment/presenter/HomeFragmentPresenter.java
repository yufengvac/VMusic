package com.vac.vmusic.homefragment.presenter;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.homefragment.adapters.MyFragmentAdapter;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.view.LocalMusicFragment;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view.OnLineMusicFragment;
import com.vac.vmusic.homefragment.view.IHomeFragment;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;

import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class HomeFragmentPresenter {
    private IHomeFragment iHomeFragment;
    private MyFragmentAdapter myFragmentAdapter;


    private HomeColorManager homeColorManager;

    public HomeFragmentPresenter(IHomeFragment iHomeFragment_){
        this.iHomeFragment = iHomeFragment_;
        String[] pageTitle = new String[]{"我的","乐库"};
        myFragmentAdapter = new MyFragmentAdapter(iHomeFragment_.getMyFm(),pageTitle);
        homeColorManager = HomeColorManager.getHomeColorManager();
    }

    public void addFragmentToViewPager(){
        TabLayout tabLayout = iHomeFragment.getTabLayout();
        ViewPager viewPager = iHomeFragment.getViewPager();

        LocalMusicFragment localMusicFragment = LocalMusicFragment.getLocalMusicFragment(null);
        OnLineMusicFragment onLineMusicFragment = OnLineMusicFragment.getOnLineMusicFragment(null);
        List<BaseSwipeBackFragment> swipeBackFragmentList = new ArrayList<>();
        swipeBackFragmentList.add(localMusicFragment);
        swipeBackFragmentList.add(onLineMusicFragment);
        myFragmentAdapter.setData(swipeBackFragmentList);
        viewPager.setAdapter(myFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        iHomeFragment.getScrollYObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer scrollY) {

                iHomeFragment.getTopContent().setBackgroundColor(homeColorManager.transferColorByScrollWithNoInitColor(scrollY));
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    RxBus.get().post("pageOffsetColor",homeColorManager.transferColorByPagerOffset(positionOffset));
                }else if(positionOffset==0&&position==0){
                    RxBus.get().post("pageOffsetColor",homeColorManager.getCurrentColor());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
