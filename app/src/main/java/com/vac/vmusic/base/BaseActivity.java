package com.vac.vmusic.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.BinderSingleton;
import com.vac.vmusic.service.PlayService;
import com.vac.vmusic.swipebackbase.SwipeBackLayout;


/**
 * Created by vac on 16/10/21.
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    public int contentViewId;

    private SwipeBackLayout mSwipeBackLayout;
    private int mDefaultFragmentBackground = 0;
    private IBinder musicIbinder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStautsColor();
//        onActivityCreate();
        getContentViewId();
        setContentView(contentViewId);
        initView();

    }

    public abstract void getContentViewId();
    public abstract void initView();

    public void setMusicIbinder(IBinder ibinder){
        musicIbinder = ibinder;
        BinderSingleton.getInstance().setMusicBinder(musicIbinder);
    }
    public void clearMusicIbinder(){
        musicIbinder = null;
    }
    public PlayService.MusicBinder getMusicIbinder(){
        return (PlayService.MusicBinder) BinderSingleton.getInstance().getMusicBinder();
    }

    public void beginStartActivity(Context fromActivity, Class claszz){
        Intent intent = new Intent(fromActivity,claszz);
        startActivity(intent);
        overridePendingTransition(R.anim.no_anim,R.anim.no_anim);
    }


    public void addFragment(FragmentManager fragmentManager, int contentId, Fragment fromFragment, Fragment toFragment) {
        if (fromFragment==null){
            fragmentManager.beginTransaction()
                    .add(contentId, toFragment, toFragment.getClass().getName())
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.h_fragment_enter, R.anim.h_fragment_exit, R.anim.h_fragment_pop_enter, R.anim.h_fragment_pop_exit)
                    .add(contentId, toFragment, toFragment.getClass().getName())
                    .hide(fromFragment)
                    .addToBackStack(toFragment.getClass().getName())
                    .commit();
        }
        if (toFragment instanceof BaseSwipeBackFragment){
            ((BaseSwipeBackFragment) toFragment).setMusicIbinder(musicIbinder);
        }

    }



//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mSwipeBackLayout.attachToActivity(this);
//    }

//    @Override
//    public View findViewById(int id) {
//        View view = super.findViewById(id);
//        if (view == null && mSwipeBackLayout != null) {
//            return mSwipeBackLayout.findViewById(id);
//        }
//        return view;
//    }

//    void onActivityCreate() {
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().getDecorView().setBackgroundDrawable(null);
//        mSwipeBackLayout = new SwipeBackLayout(this);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mSwipeBackLayout.setLayoutParams(params);
//    }

    private void setStautsColor() {
        if (Build.VERSION.SDK_INT<20){
            return;
        }
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.light_black));

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

    }
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    public boolean swipeBackPriority() {
        return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    protected void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        mDefaultFragmentBackground = backgroundRes;
    }

    int getDefaultFragmentBackground() {
        return mDefaultFragmentBackground;
    }
}
