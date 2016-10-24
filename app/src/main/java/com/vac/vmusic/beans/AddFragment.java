package com.vac.vmusic.beans;

import com.vac.vmusic.base.BaseSwipeBackFragment;

/**
 * Created by vac on 16/10/22.
 *
 */
public class AddFragment {
    private BaseSwipeBackFragment fromFragment;
    private BaseSwipeBackFragment toFragment;

    public BaseSwipeBackFragment getFromFragment() {
        return fromFragment;
    }

    public void setFromFragment(BaseSwipeBackFragment fromFragment) {
        this.fromFragment = fromFragment;
    }

    public BaseSwipeBackFragment getToFragment() {
        return toFragment;
    }

    public void setToFragment(BaseSwipeBackFragment toFragment) {
        this.toFragment = toFragment;
    }
}
