package com.vac.vmusic.playmusic.fragment.lyric.model;

import android.util.Log;

import com.vac.vmusic.beans.lyric.LyricDataXml;
import com.vac.vmusic.beans.lyric.LyricXml;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by vac on 16/11/1.
 *
 */
public class LyricFragmentModel {
    private final static String TAG = LyricFragmentModel.class.getSimpleName();
    private List<LyricXml> lyricXmlList = new ArrayList<>();
    public void downloadLyric(TingSong tingSong, final OnXmlIDDownloadListener listener){
        RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH,"XML").searchLyricIds(tingSong.getName(),tingSong.getSingerName()
        ,tingSong.getSongId(),tingSong.getSingerId()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                listener.beforeDownload();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"onError->"+throwable.getMessage());
                listener.onError(throwable.getMessage());
            }
        }).flatMap(new Func1<LyricDataXml, Observable<LyricXml>>() {
            @Override
            public Observable<LyricXml> call(LyricDataXml lyricDataXml) {
                return Observable.from(lyricDataXml.getLyricXmls());
            }
        }).subscribe(new Subscriber<LyricXml>() {
            @Override
            public void onNext(LyricXml lyricXml) {
                lyricXmlList.add(lyricXml);
            }

            @Override
            public void onCompleted() {
                listener.onLyricXmlDownloadCompleted(lyricXmlList);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e.getMessage());
            }
        });
    }

    public interface OnXmlIDDownloadListener{
        void onLyricXmlDownloadCompleted(List<LyricXml> lyricXmlList);
        void onError(String message);
        void beforeDownload();
    }
}
