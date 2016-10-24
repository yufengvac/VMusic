package com.vac.vmusic.homemain.presenter;


import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.homefragment.view.HomeFragment;
import com.vac.vmusic.homemain.view.IMainActivity;

import rx.functions.Action1;

/**
 * Created by vac on 16/10/21.
 * MainActivityPresenter
 */
public class MainActivityPresenter {
    private IMainActivity iMainActivity;
    public MainActivityPresenter(IMainActivity iMainActivity_){
        this.iMainActivity = iMainActivity_;
    }

    public void initContent(){
        iMainActivity.addHomeFragment(null, HomeFragment.getHomeFragment(null));
        iMainActivity.getAddFragmentObservable().subscribe(new Action1<AddFragment>() {
            @Override
            public void call(AddFragment addFragment) {
                iMainActivity.addHomeFragment(addFragment.getFromFragment(),addFragment.getToFragment());
            }
        });
    }
}
