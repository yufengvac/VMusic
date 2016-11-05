package com.vac.vmusic.downmusicfragment.model;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragmentModel {
    public void getDownloadMusic(final OnLocalMusicLoadListener listener){
        Observable.create(new Observable.OnSubscribe<List<LocalMusic>>() {
            @Override
            public void call(Subscriber<? super List<LocalMusic>> subscriber) {
                subscriber.onNext(DataSupport.findAll(LocalMusic.class));
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
