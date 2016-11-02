package com.vac.vmusic.playmusic.fragment.lyric.model;

import android.util.Log;

import com.vac.vmusic.beans.lyric.LyricDataXml;
import com.vac.vmusic.beans.lyric.LyricXml;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.http.apiconstant.HostType;
import com.vac.vmusic.http.retrofitmanager.RetrofitManager;
import com.vac.vmusic.utils.XmlUtil;

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
    public void downloadLyricId(TingSong tingSong, final OnXmlIDDownloadListener listener){
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
        }).map(new Func1<LyricDataXml, List<LyricXml>>() {
            @Override
            public List<LyricXml> call(LyricDataXml lyricDataXml) {
                return lyricDataXml.getLyricXmls();
            }
        }).subscribe(new Subscriber<List<LyricXml>>() {
            @Override
            public void onNext(List<LyricXml> lyricXmls) {
                listener.onLyricXmlDownloadCompleted(lyricXmls);
            }

            @Override
            public void onCompleted() {

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
    public interface OnLyricDownloadListener{
        void beforeDownload();
        void onError(String message);
        void onSuccess(String result);
    }


    public void downloadLyricContent(long lyricId,final OnLyricDownloadListener listener){
        RetrofitManager.getInstance(HostType.HOST_TYPE_SEARCH,"string").searchLyricContent(lyricId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.beforeDownload();
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"发生了错误-"+throwable.getMessage());
                throwable.printStackTrace();
                listener.onError(throwable.getMessage());
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }
}
