package com.vac.vmusic.search.normalsearch.searchtab.view;

import android.support.v7.widget.RecyclerView;

import com.vac.vmusic.views.AutoLoadMoreRecyclerView;

/**
 * Created by vac on 16/10/24.
 *
 */
public interface ISearchTabFragment {

    void showProgress();

    void hideProgress();

    void noMoreData();

    RecyclerView.Adapter getHomeAdapter();

    AutoLoadMoreRecyclerView getRecyclerView();

    String getKeyWord();
}
