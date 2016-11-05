package com.vac.vmusic.homefragment.childfragment.localmusicfragment.model;

import android.content.Context;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.utils.ContentProviderHelper;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/11/5.
 *
 */
public class LocalMusicFragmentModel {
    public void loadNativeMusic(final Context context,final OnLocalMusicLoadListener listener){
        Observable.create(new Observable.OnSubscribe<List<LocalMusic>>() {
            @Override
            public void call(Subscriber<? super List<LocalMusic>> subscriber) {
                ContentProviderHelper contentProviderHelper = new ContentProviderHelper(context);
                subscriber.onNext(contentProviderHelper.getLocalMusicList());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LocalMusic>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LocalMusic> localMusics) {
                        listener.onLocalMusicLoadListener(localMusics);
                    }
                });
    }
}
