package com.vac.vmusic.songlistdetail.model;

import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.beans.detail.HttpAlbumDetail;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.songlist.SongListDetail;
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
    public void loadDetailData(long albumId, final RequestCallback<AlbumDetail> callback){
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

    public void loadSongListDetailData(long songlistId, final RequestCallback<SongListDetail> callback){
        RetrofitManager.getInstance(HostType.HOST_TYPE_TTPOD).getSongListDetail(songlistId)
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
        }).subscribe(new Subscriber<SongListDetail>() {
            @Override
            public void onCompleted() {
                callback.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callback.requestError(e.getMessage());
            }

            @Override
            public void onNext(SongListDetail songListDetail) {
                List<SongListDetail> songListDetailList = new ArrayList<>();
                songListDetailList.add(songListDetail);
                callback.requestSuccess(songListDetailList);
            }
        });
    }
}
