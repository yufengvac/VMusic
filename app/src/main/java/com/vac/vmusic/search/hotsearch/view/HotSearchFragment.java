package com.vac.vmusic.search.hotsearch.view;

import android.os.Bundle;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.views.WordWrapView;

/**
 * Created by vac on 16/10/24.
 *
 */
public class HotSearchFragment extends BaseSwipeBackFragment {

    private WordWrapView wordWrapView;
    public static HotSearchFragment getHotSearchFragment(Bundle bundle){
        HotSearchFragment hotSearchFragment = new HotSearchFragment();
        if (bundle!=null){
            hotSearchFragment.setArguments(bundle);
        }
        return hotSearchFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.hot_search_fragment;
    }

    @Override
    public void initView(View view) {
        wordWrapView = (WordWrapView) view.findViewById(R.id.hot_search_fragment_wordwrapView);
    }

}
