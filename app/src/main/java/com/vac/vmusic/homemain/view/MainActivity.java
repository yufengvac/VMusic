package com.vac.vmusic.homemain.view;


import android.support.v4.app.Fragment;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.homemain.presenter.MainActivityPresenter;
import com.vac.vmusic.utils.RxBus;

import rx.Observable;

public class MainActivity extends BaseActivity implements IMainActivity{

    private Observable<AddFragment> addFragmentObservable;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.activity_main;
    }

    @Override
    public void initView() {

        addFragmentObservable = RxBus.get().register("addFragment", AddFragment.class);
        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.initContent();
//        setStautsColor();
    }

    public Observable<AddFragment> getAddFragmentObservable() {
        return addFragmentObservable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("addFragment",addFragmentObservable);
    }

    @Override
    public void addHomeFragment(Fragment fromFragment, Fragment toFragment) {
        addFragment(getSupportFragmentManager(),R.id.main_content,fromFragment,toFragment);
    }
}
