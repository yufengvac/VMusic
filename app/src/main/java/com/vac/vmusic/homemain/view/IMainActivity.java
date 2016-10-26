package com.vac.vmusic.homemain.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.AddFragment;

import rx.Observable;

/**
 * Created by vac on 16/10/21.
 * MainActivity 结论
 */
public interface IMainActivity {
    void addHomeFragment(Fragment fromFragment, Fragment toFragment);
    Observable<AddFragment> getAddFragmentObservable();
    void initService();
}
