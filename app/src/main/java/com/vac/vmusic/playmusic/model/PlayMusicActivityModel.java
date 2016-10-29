package com.vac.vmusic.playmusic.model;

import android.util.Log;

import com.vac.vmusic.beans.httpresult.HttpResultPic;
import com.vac.vmusic.beans.search.artistpic.ArtistPic;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by vac on 16/10/29.
 *
 */
public class PlayMusicActivityModel {
    public void searchArtistPics(final String artist, final RequestCallback<PicUrls> callback){
        RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH)
                .searchArtistPics(artist)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("PlayMusicActivityModel","发生了Error"+throwable.getMessage());
                callback.requestError(throwable.getMessage());
            }
        }).map(new Func1<HttpResultPic<ArtistPic<PicUrls>>, ArtistPic<PicUrls>>() {
            @Override
            public ArtistPic<PicUrls> call(HttpResultPic<ArtistPic<PicUrls>> artistPicHttpResultPic) {
                ArtistPic<PicUrls>[] artistPic = artistPicHttpResultPic.getData();
                if (artistPic.length>0){
                    return artistPic[0];
                }
                return null;
            }
        }).map(new Func1<ArtistPic<PicUrls>, PicUrls[]>() {
            @Override
            public PicUrls[] call(ArtistPic<PicUrls> picUrlsArtistPic) {
                PicUrls[] p = picUrlsArtistPic.getPicUrls();
                if (p.length>0){
                    return p;
                }
                return null;
            }
        }).subscribe(new Subscriber<PicUrls[]>() {
            @Override
            public void onCompleted() {
                callback.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callback.requestError(e.getMessage());
            }

            @Override
            public void onNext(PicUrls[] picUrlses) {
                List<PicUrls> list = new ArrayList<>();
                for (int i=0;i<picUrlses.length;i++){
                    list.add(picUrlses[i]);
                }
                callback.requestSuccess(list);
            }
        });
    }
}
