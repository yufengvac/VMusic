package com.vac.vmusic.skinfragment.activity.model;

import android.util.Log;

import com.vac.vmusic.beans.skin.HttpSkin;
import com.vac.vmusic.beans.skin.Skin;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by vac on 16/11/7.
 *
 */
public class SkinActivityModel {
    public void getWallPager(final RequestCallback<Skin> callback){

        RetrofitManager.getInstance(HostType.HOST_TYPE_WALLPAPER).getWallPager()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("SkinActivityModel","发生了error");
                throwable.printStackTrace();
            }
        }).map(new Func1<HttpSkin<Skin>, List<Skin>>() {
            @Override
            public List<Skin> call(HttpSkin<Skin> skinHttpSkin) {
                return skinHttpSkin.getData();
            }
        }).subscribe(new Subscriber<List<Skin>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Skin> skin) {
                callback.requestSuccess(skin);
            }
        });
    }
}
