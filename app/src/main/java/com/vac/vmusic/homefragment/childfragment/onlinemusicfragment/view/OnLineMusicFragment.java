package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.presenter.OnLineMusicFragmentPresenter;
import com.vac.vmusic.utils.RxBus;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class OnLineMusicFragment extends BaseSwipeBackFragment implements IOnLineMusicFragment{

    private View topView;
    private Observable<Integer> colorObservable;

    public static OnLineMusicFragment getOnLineMusicFragment(Bundle bundle){
        OnLineMusicFragment onLineMusicFragment = new OnLineMusicFragment();
        if (bundle!=null){
            onLineMusicFragment.setArguments(bundle);
        }
        return onLineMusicFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.online_music_fragment;
//        colorObservable = RxBus.get().register("pageOffsetColor",Integer.class);

    }

    @Override
    public void initView(View view) {
        topView = view.findViewById(R.id.online_music_fragment_top_view);

        colorObservable = RxBus.get().register("pageOffsetColor",Integer.class);
        OnLineMusicFragmentPresenter onLineMusicFragmentPresenter = new OnLineMusicFragmentPresenter(this);
        onLineMusicFragmentPresenter.watchTopViewColorByScroll();

    }

    @Override
    public View getTopView() {
        return topView;
    }

    @Override
    public Observable<Integer> getColorObservable() {
        return colorObservable;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("pageOffsetColor",colorObservable);
    }
}
