package com.vac.vmusic.homemain.model;

import android.util.Log;

import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/10/27.
 *
 */
public class MainActivityModel implements IMainActivityModel {
    @Override
    public void searchArtist(String q, final RequestCallback<TingArtist> callback) {
        RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH).searchArtist(q)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG","onError"+throwable.getMessage());
                callback.requestError(throwable.getMessage());
            }
        }).subscribe(new Subscriber<HttpResultPlus<TingArtist>>() {
            @Override
            public void onCompleted() {
                callback.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callback.requestError(e.getMessage());
            }

            @Override
            public void onNext(HttpResultPlus<TingArtist> tingArtistHttpResultPlus) {
                callback.requestSuccess(tingArtistHttpResultPlus.getData());
            }
        });
    }

    @Override
    public void loadPlayList(final RequestCallback<TingSong> callback) {
        Observable.create(new Observable.OnSubscribe<List<TingSong>>() {
            @Override
            public void call(Subscriber<? super List<TingSong>> subscriber) {
                subscriber.onNext(DataSupport.findAll(TingSong.class,true));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TingSong>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TingSong> tingSongList) {
                        callback.requestSuccess(tingSongList);
                    }
                });
    }
}
