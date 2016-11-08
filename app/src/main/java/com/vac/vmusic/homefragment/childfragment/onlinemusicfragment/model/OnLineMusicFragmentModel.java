package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.model;

import android.util.Log;

import com.vac.vmusic.beans.discover.DiscoverColumn;
import com.vac.vmusic.beans.discover.DiscoverColumnData;
import com.vac.vmusic.beans.discover.HttpData;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view.OnLineMusicFragment;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by vac on 16/11/8.
 *
 */
public class OnLineMusicFragmentModel {
    private static final String TAG = OnLineMusicFragmentModel.class.getSimpleName();
    public void getDiscoverData(final RequestCallback<DiscoverColumn<DiscoverColumnData>> callback){
        RetrofitManager.getInstance(HostType.HOST_TYPE_WALLPAPER).getDiscoverData()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }).subscribe(new Subscriber<HttpData<DiscoverColumn<DiscoverColumnData>>>() {
            @Override
            public void onCompleted() {
                callback.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpData<DiscoverColumn<DiscoverColumnData>> discoverColumnHttpData) {
                callback.requestSuccess(discoverColumnHttpData.getData());
            }
        });
    }
}
