package com.vac.vmusic.splashscreen;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.homemain.view.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/10/21.
 * 闪屏页
 */
public class SplashScreenActivity extends BaseActivity{
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.splash_screen;
    }

    @Override
    public void initView() {
        getWindow().setBackgroundDrawable(null);
        Observable.timer(3, TimeUnit.SECONDS,AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                beginStartActivity(SplashScreenActivity.this, MainActivity.class);
                finish();
            }
        });
    }

}
