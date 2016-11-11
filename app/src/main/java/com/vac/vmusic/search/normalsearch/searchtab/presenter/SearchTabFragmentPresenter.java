package com.vac.vmusic.search.normalsearch.searchtab.presenter;


import android.content.ContentValues;

import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.beans.search.historysearch.HistorySearch;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchAlbumAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchMVAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchSongAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.adapter.SearchSongListAdapter;
import com.vac.vmusic.search.normalsearch.searchtab.model.SearchTabFragmentModel;
import com.vac.vmusic.search.normalsearch.searchtab.view.ISearchTabFragment;
import com.vac.vmusic.search.normalsearch.searchtab.view.SearchTabFragment;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchTabFragmentPresenter implements RequestCallback<TingSong> {
    private ISearchTabFragment iSearchTabFragment;
    private SearchTabFragmentModel searchTabFragmentModel;

    private SearchAlbumRequestCallBack searchAlbumRequestCallBack;
    private SearchSongListRequestCallBack searchSongListRequestCallBack;
    private RequestSearchMVCallback searchMVCallback;

    /**每个列表第一页显示30条最多*/
    private static final int LIMIT = 20;

    private Map<String,Boolean> mIsRefreshMap = new HashMap<>();
    private Map<String,Integer> pageMap = new HashMap<>();
    private String curType;

    public SearchTabFragmentPresenter(ISearchTabFragment _homeFragment){
        iSearchTabFragment = _homeFragment;

        if (searchTabFragmentModel==null){
            searchTabFragmentModel = new SearchTabFragmentModel();
        }

        if (searchAlbumRequestCallBack==null){
            searchAlbumRequestCallBack = new SearchAlbumRequestCallBack();
        }

        if (searchSongListRequestCallBack==null){
            searchSongListRequestCallBack = new SearchSongListRequestCallBack();
        }

        searchMVCallback = new RequestSearchMVCallback();

    }

    public void loadData(String type){
        if (iSearchTabFragment.getKeyWord().isEmpty()){
            iSearchTabFragment.hideProgress();
            return;
        }
        curType = type;
        mIsRefreshMap.put(type,true);
        pageMap.put(type,1);
        if (SearchTabFragment.HomeFragmentType.SINGLE.equals(type)){
            searchTabFragmentModel.searchSong(LIMIT,pageMap.get(type),iSearchTabFragment.getKeyWord(),this);
        }else if (SearchTabFragment.HomeFragmentType.ALBUM.equals(type)){
            searchTabFragmentModel.searchAlbum(LIMIT, pageMap.get(type), iSearchTabFragment.getKeyWord(),searchAlbumRequestCallBack);
        }else if (SearchTabFragment.HomeFragmentType.SONGLIST.equals(type)){
            searchTabFragmentModel.searchSongList(LIMIT,pageMap.get(type),iSearchTabFragment.getKeyWord(),searchSongListRequestCallBack);
        }else if (SearchTabFragment.HomeFragmentType.MV.equals(type)){
            searchTabFragmentModel.searchMV(LIMIT,pageMap.get(type),iSearchTabFragment.getKeyWord(),searchMVCallback);
        }

        HistorySearch historySearch = new HistorySearch();
        historySearch.setTimeStamp(System.currentTimeMillis());
        historySearch.setContent(iSearchTabFragment.getKeyWord());
        List<HistorySearch>  queryHistorySearchList = DataSupport.where("content = ?",iSearchTabFragment.getKeyWord()).find(HistorySearch.class);
        if (queryHistorySearchList==null||queryHistorySearchList.size()==0){
            historySearch.setCount(1);
            historySearch.save();
        }

    }

    public void loadDataMore(String type){
        mIsRefreshMap.put(type,false);
        int page = pageMap.get(type);
        if (SearchTabFragment.HomeFragmentType.SINGLE.equals(type)){
            searchTabFragmentModel.searchSong(LIMIT,page,iSearchTabFragment.getKeyWord(),this);
        }else if (SearchTabFragment.HomeFragmentType.ALBUM.equals(type)){
            searchTabFragmentModel.searchAlbum(LIMIT, page, iSearchTabFragment.getKeyWord(),searchAlbumRequestCallBack);
        }else if (SearchTabFragment.HomeFragmentType.SONGLIST.equals(type)){
            searchTabFragmentModel.searchSongList(LIMIT,page,iSearchTabFragment.getKeyWord(),searchSongListRequestCallBack);
        }else if (SearchTabFragment.HomeFragmentType.MV.equals(type)){
            searchTabFragmentModel.searchMV(LIMIT,page,iSearchTabFragment.getKeyWord(),searchMVCallback);
        }
    }

    private class SearchAlbumRequestCallBack implements RequestCallback<TingAlbum>{
        @Override
        public void beforeRequest() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.showProgress();
            }
        }

        @Override
        public void requestCompleted() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.hideProgress();
            }
        }

        @Override
        public void requestSuccess(List<TingAlbum> data) {
            ((SearchAlbumAdapter)iSearchTabFragment.getHomeAdapter()).hideFooter();
            ((SearchAlbumAdapter)iSearchTabFragment.getHomeAdapter()).setData(data,mIsRefreshMap.get(curType));
            iSearchTabFragment.getRecyclerView().notifyMoreLoaded();
            if (data==null||data.size()==0){
                iSearchTabFragment.noMoreData();
            }else {
                int curPage = pageMap.get(SearchTabFragment.HomeFragmentType.ALBUM);
                curPage++;
                pageMap.put(SearchTabFragment.HomeFragmentType.ALBUM,curPage);
            }
        }

        @Override
        public void requestError(String errorMsg) {

        }
    }

    private class SearchSongListRequestCallBack implements RequestCallback<TingSongList>{
        @Override
        public void beforeRequest() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.showProgress();
            }
        }

        @Override
        public void requestCompleted() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.hideProgress();
            }
        }

        @Override
        public void requestSuccess(List<TingSongList> data) {
            ((SearchSongListAdapter)iSearchTabFragment.getHomeAdapter()).hideFooter();
            ((SearchSongListAdapter)iSearchTabFragment.getHomeAdapter()).setData(data,mIsRefreshMap.get(curType));
            iSearchTabFragment.getRecyclerView().notifyMoreLoaded();
            if (data==null||data.size()==0){
                iSearchTabFragment.noMoreData();
            }else {
                int curPage = pageMap.get(SearchTabFragment.HomeFragmentType.SONGLIST);
                curPage++;
                pageMap.put(SearchTabFragment.HomeFragmentType.SONGLIST,curPage);
            }
        }

        @Override
        public void requestError(String errorMsg) {

        }
    }

    private class RequestSearchMVCallback implements RequestCallback<TingSearchMV>{
        @Override
        public void beforeRequest() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.showProgress();
            }
        }

        @Override
        public void requestCompleted() {
            if (mIsRefreshMap.get(curType)){
                iSearchTabFragment.hideProgress();
            }
        }

        @Override
        public void requestSuccess(List<TingSearchMV> data) {
            ((SearchMVAdapter)iSearchTabFragment.getHomeAdapter()).hideFooter();
            ((SearchMVAdapter)iSearchTabFragment.getHomeAdapter()).setData(data,mIsRefreshMap.get(curType));
            iSearchTabFragment.getRecyclerView().notifyMoreLoaded();
            if (data==null||data.size()==0){
                iSearchTabFragment.noMoreData();
            }else {
                int curPage = pageMap.get(SearchTabFragment.HomeFragmentType.MV);
                curPage++;
                pageMap.put(SearchTabFragment.HomeFragmentType.MV,curPage);
            }
        }

        @Override
        public void requestError(String errorMsg) {

        }
    }

    @Override
    public void beforeRequest() {
        if (mIsRefreshMap.get(curType)){
            iSearchTabFragment.showProgress();
        }

    }

    @Override
    public void requestCompleted() {
        if (mIsRefreshMap.get(curType)){
            iSearchTabFragment.hideProgress();
        }
    }

    @Override
    public void requestSuccess(List<TingSong> data) {
        ((SearchSongAdapter)iSearchTabFragment.getHomeAdapter()).hideFooter();
        ((SearchSongAdapter)iSearchTabFragment.getHomeAdapter()).setData(data,mIsRefreshMap.get(curType));
        iSearchTabFragment.getRecyclerView().notifyMoreLoaded();
        if (data==null||data.size()==0){
            iSearchTabFragment.noMoreData();
        }else {
            int curPage = pageMap.get(SearchTabFragment.HomeFragmentType.SINGLE);
            curPage++;
            pageMap.put(SearchTabFragment.HomeFragmentType.SINGLE,curPage);
        }
    }

    @Override
    public void requestError(String errorMsg) {
        iSearchTabFragment.hideProgress();
    }
}
