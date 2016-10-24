package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.presenter;

import android.util.Log;

import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view.IOnLineMusicFragment;
import com.vac.vmusic.utils.RxBus;

import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class OnLineMusicFragmentPresenter {
    private IOnLineMusicFragment iOnLineMusicFragment;
    public OnLineMusicFragmentPresenter(IOnLineMusicFragment iOnLineMusicFragment_){
        this.iOnLineMusicFragment = iOnLineMusicFragment_;

    }
    public void watchTopViewColorByScroll(){
        iOnLineMusicFragment.getColorObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                iOnLineMusicFragment.getTopView().setBackgroundColor(integer);
            }
        });
    }
}
