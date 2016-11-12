package com.vac.vmusic.songlistdetail.model;

import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.beans.detail.HttpAlbumDetail;
import com.vac.vmusic.beans.search.TingAlbum;
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
 * Created by vac on 16/11/12.
 *
 */
public class SongListDetailFraModel {
    public void loadDetailDate(long albumId, final RequestCallback<AlbumDetail> callback){
        RetrofitManager.getInstance(HostType.HOST_TYPE_WALLPAPER).getAlbumDetailData(albumId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callback.requestError(throwable.getMessage());
                throwable.printStackTrace();
            }
        }).map(new Func1<HttpAlbumDetail, AlbumDetail>() {
            @Override
            public AlbumDetail call(HttpAlbumDetail httpAlbumDetail) {
                return httpAlbumDetail.getData();
            }
        }).subscribe(new Subscriber<AlbumDetail>() {
            @Override
            public void onCompleted() {
                callback.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AlbumDetail albumDetail) {
                List<AlbumDetail> tingAlbums = new ArrayList<>();
                tingAlbums.add(albumDetail);
                callback.requestSuccess(tingAlbums);
            }
        });
    }
}
