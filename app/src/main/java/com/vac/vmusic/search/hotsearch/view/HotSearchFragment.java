package com.vac.vmusic.search.hotsearch.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.search.hotsearch.presenter.HotSearchFragmentPresenter;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.views.BorderTextView;
import com.vac.vmusic.views.WordWrapView;

/**
 * Created by vac on 16/10/24.
 *
 */
public class HotSearchFragment extends BaseSwipeBackFragment implements IHotSearchFragment{

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
        WordWrapView wordWrapView = (WordWrapView) view.findViewById(R.id.hot_search_fragment_wordwrapView);
        BorderTextView historyTextView = (BorderTextView)view.findViewById(R.id.hot_search_fragment_history_search_border_text_view);
        historyTextView.setBorderColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        HotSearchFragmentPresenter hotSearchFragmentPresenter = new HotSearchFragmentPresenter(this);
        hotSearchFragmentPresenter.loadHistorySearch(wordWrapView);
    }


    @Override
    public Context getMyContext() {
        return getActivity();
    }
}
