package com.vac.vmusic.service.binder;

import android.os.Binder;
import android.util.Log;

import com.vac.vmusic.beans.lyric.LyricSentence;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.service.service.IService;
import com.vac.vmusic.service.service.PlayService;
import com.vac.vmusic.utils.LyricLoadHelper;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/10/28.
 *
 */
public class MusicBinder extends Binder implements IMusicBinder{
    private IService iService;
    private List<OnPlayMusicStateListener> onPlayMusicStateListenerList;

    private List<LyricLoadHelper.LyricListener> onLyricListenerList;

    public MusicBinder(IService iService_){
        this.iService = iService_;
        onPlayMusicStateListenerList = iService_.getOnPlayMusicStateListener();
        onLyricListenerList = iService_.getOnLyricListener();
    }

    @Override
    public void registerOnPlayMusicStateListener(OnPlayMusicStateListener listener){
        onPlayMusicStateListenerList.add(listener);
    }
    @Override
    public void unRegisterOnPlayMusicStateListener(OnPlayMusicStateListener listener){
        onPlayMusicStateListenerList.remove(listener);
    }

    @Override
    public void registerLyricListener(LyricLoadHelper.LyricListener lyricListener) {
        onLyricListenerList.add(lyricListener);
    }

    @Override
    public void unRegisterLyricListener(LyricLoadHelper.LyricListener lyricListener) {
        onLyricListenerList.remove(lyricListener);
    }

    @Override
    public void playNext(){
        iService.requestToPlayNext(true);
    }
    @Override
    public void playPre(){
        iService.requestToPlayPrevious(true);
    }
    @Override
    public void playPause(){
        iService.requestToPause();
    }
    @Override
    public void togglePlay(){
        iService.requestTogglePlay();
    }

    @Override
    public void initToPlay(int position,TingSong tingSong){
        iService.setRequestMusicPosition(position);
        iService.setRequestPlayMusicId(tingSong.getSongId());
    }
    @Override
    public void beginToPlay(int position,TingSong tingSong){
        initToPlay(position,tingSong);
        iService.requestToPlay();
    }
    @Override
    public void seekToSpecifiedPosition(int milliSeconds) {
        if (iService.getPlayerState() != PlayService.PlayState.Stopped) {
            iService.getPlayer().seekTo(milliSeconds);
        }
    }

    @Override
    public void setMusicPlayList(final List<TingSong> tingSongList, boolean isNeedSave) {
        if (tingSongList != null && tingSongList.size() > 0) {
            if (isNeedSave) {

                Observable.create(new Observable.OnSubscribe<Void>() {
                    @Override
                    public void call(Subscriber<? super Void> subscriber) {
                        DataSupport.deleteAll(TingSong.class);
                        DataSupport.deleteAll(TingAudition.class);

                        for (int i = 0; i < tingSongList.size(); i++) {
                            TingSong tingSong = tingSongList.get(i);
                            List<TingAudition> tingAuditions = tingSong.getAuditionList();
                            if (tingAuditions != null && tingAuditions.size() > 0) {
                                for (int j = 0; j < tingAuditions.size(); j++) {
                                    tingAuditions.get(j).save();
                                }
                            }
                            tingSong.save();
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                Log.e("TAG","存储成功!");
                            }
                        });


            }
            iService.getPlayingMusicList().clear();
            iService.getPlayingMusicList().addAll(tingSongList);
        }
    }
    @Override
    public List<TingSong> getMusicPlayList(){
        return iService.getPlayingMusicList();
    }

    @Override
    public int getCurrentPlayingPosition(){
        return iService.getPlayingMusicPosition();
    }
    /**
     * 改变播放模式
     */
    @Override
    public void changePlayMode(){
        int mPlayMode = (iService.getPlayerMode()+1)%4;
        if(iService.getPlayer()!=null){
            if(mPlayMode== PlayService.PlayMode.REPEAT_SINGLE){
                iService.getPlayer().setLooping(true);
            }else{
                iService.getPlayer().setLooping(false);
            }
        }

        for(int i=0;i<onPlayMusicStateListenerList.size();i++){
            onPlayMusicStateListenerList.get(i).onPlayModeChanged(mPlayMode);
        }
        iService.setPlayerMode(mPlayMode);
    }

    public int getPlayMode(){
        return iService.getPlayerMode();
    }

    @Override
    public TingSong getCurrentSong() {
        return iService.getCurrentSong();
    }

    @Override
    public int getCurrentState() {
        return iService.getPlayerState();
    }

    @Override
    public void setLyricContent(String lyric) {
        iService.setLyricContent(lyric);
    }

    @Override
    public List<LyricSentence> getLyricSentenceList() {
        return iService.getLyricSentenceList();
    }

    @Override
    public TingSong getTingSongById(long songId) {
        return iService.getTingSongById(songId);
    }
}
