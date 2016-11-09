package com.vac.vmusic.search.normalsearch.searchtab.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.mvdetail.view.MvDetailActivity;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchAlbumAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchMVAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchSongAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchSongListAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.presenter.SearchTabFragmentPresenter;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.utils.ViewUtil;
import com.vac.vmusic.views.AutoLoadMoreRecyclerView;
import com.vac.vmusic.views.DividerItemDecoration;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/24.
 *
 */
public class SearchTabFragment extends BaseSwipeBackFragment implements ISearchTabFragment {

    private AutoLoadMoreRecyclerView autoLoadMoreRecyclerView;
    private String type;
    private RecyclerView.Adapter homeAdapter;
    private SearchTabFragmentPresenter searchTabFragmentPresenter;
    private LinearLayout refreshLayout;
    private String keyWord;
    private MusicBinder musicBinder;

    public static SearchTabFragment newInstance(String type,Bundle b){
        SearchTabFragment searchTabFragment = new SearchTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        bundle.putString("keyword",b.getString("keyword"));
        searchTabFragment.setArguments(bundle);

        return searchTabFragment;
    }

    public static class HomeFragmentType{
        public static String SINGLE = "singleSong";
        public static String ALBUM = "album";
        public static String MV = "MV";
        public static String SONGLIST = "songlist";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            type = getArguments().getString("type");
            keyWord = getArguments().getString("keyword");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.search_tab_fragment;
    }

    @Override
    public void initView(View view) {
        autoLoadMoreRecyclerView = (AutoLoadMoreRecyclerView) view.findViewById(R.id.home_fragment_recyclerView);
        refreshLayout = (LinearLayout) view.findViewById(R.id.home_fragment_refresh);

        musicBinder = getMusicIbinder();

        if (type.equals(HomeFragmentType.SINGLE)){
            homeAdapter = new SearchSongAdapter(getActivity(), new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (view instanceof ImageView){

                    }else if(view instanceof LinearLayout){
                        musicBinder.setMusicPlayList(((SearchSongAdapter)homeAdapter).getmData(),true);
                        musicBinder.beginToPlay(position,((SearchSongAdapter)homeAdapter).getmData().get(position));
                    }
                }

            });
        }else if (type.equals(HomeFragmentType.ALBUM)){
            homeAdapter = new SearchAlbumAdapter(getActivity());
        }else if (type.equals(HomeFragmentType.MV)){
            homeAdapter = new SearchMVAdapter(getActivity(), new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (view instanceof LinearLayout){
                        Intent intent = new Intent(getActivity(),MvDetailActivity.class);
                        intent.putExtra("url",((SearchMVAdapter)homeAdapter).getData().get(position).getMvList().get(0).getUrl());
                        getActivity().startActivity(intent);
                    }
                }
            });
        }else if (type.equals(HomeFragmentType.SONGLIST)){
            homeAdapter = new SearchSongListAdapter(getActivity());
        }

        autoLoadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        autoLoadMoreRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        autoLoadMoreRecyclerView.setAdapter(homeAdapter);
        searchTabFragmentPresenter = new SearchTabFragmentPresenter(this);
        searchTabFragmentPresenter.loadData(type);

        autoLoadMoreRecyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                searchTabFragmentPresenter.loadDataMore(type);
            }
        });
    }

    @Override
    public String getKeyWord() {
        if (keyWord==null||keyWord.isEmpty()){
            Log.i("TAG","getKeyWord===null");
            return "";
        }
        return keyWord;
    }

    @Override
    public RecyclerView.Adapter getHomeAdapter() {
        return homeAdapter;
    }

    @Override
    public AutoLoadMoreRecyclerView getRecyclerView() {
        return autoLoadMoreRecyclerView;
    }

    @Override
    public void hideProgress() {
        ViewUtil.hideRefreshLayout(refreshLayout);
    }

    @Override
    public void showProgress() {
        ViewUtil.showRefreshLayout(refreshLayout,"正在搜索"+getKeyWord());
    }

    @Override
    public void noMoreData() {

    }

}
