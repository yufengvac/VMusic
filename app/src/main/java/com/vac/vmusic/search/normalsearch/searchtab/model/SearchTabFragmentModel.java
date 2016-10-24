package com.vac.vmusic.search.normalsearch.searchtab.model;

import android.util.Log;

import com.vac.vmusic.beans.httpresult.HttpResult;
import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;
import com.vac.vmusic.search.normalsearch.searchtab.view.SearchTabFragment;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchTabFragmentModel implements ISearchTabFragmentModel{
    private static final String TAG = SearchTabFragment.class.getSimpleName();
    @Override
    public Subscription searchSong(int size, int page, String q, final RequestCallback<TingSong> callback) {
        return RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH)
                .searchSong(size,page,q)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"发生了错误doOnError->"+throwable.getMessage());
                        callback.requestError(throwable.getMessage());
                    }
                }).map(new Func1<HttpResult<TingSong>,List<TingSong>>() {
                    @Override
                    public List<TingSong> call(HttpResult<TingSong> listHttpResult) {
                        return listHttpResult.getData();
                    }
                }).subscribe(new Subscriber<List<TingSong>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"发生了错误onError->"+e.getMessage());
                        callback.requestError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TingSong> tingSongLists) {
                        callback.requestSuccess(tingSongLists);
                    }
                });
    }

    @Override
    public Subscription searchAlbum(int size, int page, String q, final RequestCallback<TingAlbum> callback) {
        return RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH)
                .searchAlbum(size,page,q).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"发生了错误,"+throwable.getMessage());
                        callback.requestError(throwable.getMessage());
                    }
                }).map(new Func1<HttpResult<TingAlbum>, List<TingAlbum>>() {
                    @Override
                    public List<TingAlbum> call(HttpResult<TingAlbum> tingAlbumHttpResult) {
                        return tingAlbumHttpResult.getData();
                    }
                }).subscribe(new Subscriber<List<TingAlbum>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TingAlbum> tingAlba) {
                        callback.requestSuccess(tingAlba);
                    }
                });
    }

    @Override
    public Subscription searchSongList(int size, int page, String q, final RequestCallback<TingSongList> callback) {
        return RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH)
                .searchSongList(size,page,q).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"发生了错误,"+throwable.getMessage());
                        callback.requestError(throwable.getMessage());
                    }
                }).map(new Func1<HttpResultPlus<TingSongList>, List<TingSongList>>() {
                    @Override
                    public List<TingSongList> call(HttpResultPlus<TingSongList> tingSongListHttpResultPlus) {
                        return tingSongListHttpResultPlus.getData();
                    }
                }).subscribe(new Subscriber<List<TingSongList>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TingSongList> tingSongLists) {
                        callback.requestSuccess(tingSongLists);
                    }
                });
    }

    @Override
    public Subscription searchMV(int size, int page, String q, final RequestCallback<TingSearchMV> callback) {
        return RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH).searchMV(size,page, q)
                .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                callback.beforeRequest();
                            }
                        }
                ).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"发生了错误"+throwable.getMessage());
                        callback.requestError(throwable.getMessage());
                    }
                }).map(new Func1<HttpResultPlus<TingSearchMV>, List<TingSearchMV>>() {
                    @Override
                    public List<TingSearchMV> call(HttpResultPlus<TingSearchMV> tingSearchMVHttpResultPlus) {
                        return tingSearchMVHttpResultPlus.getData();
                    }
                }).subscribe(new Subscriber<List<TingSearchMV>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TingSearchMV> tingSearchMVs) {
                        callback.requestSuccess(tingSearchMVs);
                    }
                });
    }
}
