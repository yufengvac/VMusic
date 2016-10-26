package com.vac.vmusic.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.vac.vmusic.R;
import com.vac.vmusic.beans.BinderSingleton;
import com.vac.vmusic.service.PlayService;
import com.vac.vmusic.swipebackbase.SwipeBackLayout;


/**
 *
 */
public abstract class BaseSwipeBackFragment extends Fragment {
//    protected OnAddFragmentListener mAddFragmentListener;
    private View mFragmentRootView;
    public int fragmentViewId;

//    private static final String SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN = "SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN";
//    private SwipeBackLayout mSwipeBackLayout;
//    private Animation mNoAnim;
    public  boolean mLocking = false;

    protected BaseActivity _mActivity;

    private IBinder musicIbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null==mFragmentRootView){
            getFragmentViewId();
            mFragmentRootView = inflater.inflate(fragmentViewId,container,false);
            initView(mFragmentRootView);
        }

        return mFragmentRootView;
    }

    public abstract void getFragmentViewId();
    public abstract void initView(View view);


    public void setMusicIbinder(IBinder ibinder){
        if (musicIbinder==null&&ibinder!=null){
            BinderSingleton.getInstance().setMusicBinder(ibinder);
        }
    }
    public PlayService.MusicBinder getMusicIbinder(){
       return (PlayService.MusicBinder) BinderSingleton.getInstance().getMusicBinder();
    }

//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof BaseActivity) {
//            _mActivity = (BaseActivity) activity;
//        } else {
//            throw new RuntimeException(activity.toString() + " must extends SwipeBackActivity");
//        }
//    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            boolean isSupportHidden = savedInstanceState.getBoolean(SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN);
//
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            if (isSupportHidden) {
//                ft.hide(this);
//            } else {
//                ft.show(this);
//            }
//            ft.commit();
//        }
//
//        mNoAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim);
//        onFragmentCreate();
//    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN, isHidden());
//    }

//    private void onFragmentCreate() {
//        mSwipeBackLayout = new SwipeBackLayout(getActivity());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mSwipeBackLayout.setLayoutParams(params);
//        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
//    }
//
//    protected View attachToSwipeBack(View view) {
//        mSwipeBackLayout.attachToFragment(this, view);
//        return mSwipeBackLayout;
//    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden && mSwipeBackLayout != null) {
//            mSwipeBackLayout.hiddenFragment();
//        }
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        View view = getView();
//        initFragmentBackground(view);
//        if (view != null) {
//            view.setClickable(true);
//        }
//    }

//    private void initFragmentBackground(View view) {
//        if (view instanceof SwipeBackLayout) {
//            View childView = ((SwipeBackLayout) view).getChildAt(0);
//            setBackground(childView);
//        } else {
//            setBackground(view);
//        }
//    }

//    private void setBackground(View view) {
//        if (view != null && view.getBackground() == null) {
//            int defaultBg = _mActivity.getDefaultFragmentBackground();
//            if (defaultBg == 0) {
//                int background = getWindowBackground();
//                view.setBackgroundResource(background);
//            } else {
//                view.setBackgroundResource(defaultBg);
//            }
//        }
//    }

//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (mLocking) {
//            return mNoAnim;
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }

//    protected int getWindowBackground() {
//        TypedArray a = getActivity().getTheme().obtainStyledAttributes(new int[]{
//                android.R.attr.windowBackground
//        });
//        int background = a.getResourceId(0, 0);
//        a.recycle();
//        return background;
//    }
//
//    public SwipeBackLayout getSwipeBackLayout() {
//        return mSwipeBackLayout;
//    }
//
//    public void setSwipeBackEnable(boolean enable) {
//        mSwipeBackLayout.setEnableGesture(enable);
//    }

}
