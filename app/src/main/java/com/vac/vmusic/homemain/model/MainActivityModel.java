package com.vac.vmusic.homemain.model;

import android.util.Log;

import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

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
}
