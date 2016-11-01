package com.vac.vmusic.playmusic.fragment.relative.view;

import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;

/**
 * Created by vac on 16/11/1.
 *
 */
public class RelativeFragment extends BaseSwipeBackFragment{
    public static RelativeFragment getRelativedFragment(){
        RelativeFragment relativeFragment = new RelativeFragment();
        return relativeFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.relatived_fragment;
    }

    @Override
    public void initView(View view) {

    }
}
