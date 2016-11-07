package com.vac.vmusic.skinfragment.activity.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.skinfragment.activity.presenter.SkinActivityPresenter;

/**
 * Created by vac on 16/11/7.
 *
 */
public class SkinActivity extends BaseActivity implements ISkinActivity{
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private SkinActivityPresenter skinActivityPresenter;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.activity_change_skin;
    }

    @Override
    public void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.change_skin_rg);
        viewPager = (ViewPager) findViewById(R.id.change_skin_viewPager);
        skinActivityPresenter = new SkinActivityPresenter(this);
        skinActivityPresenter.loadData();
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        view.setPadding(0, 0, 0,0);
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.x = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindowManager().updateViewLayout(view, lp);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        skinActivityPresenter.removeOnPageListener();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public FragmentManager getFm() {
        return getSupportFragmentManager();
    }
}
