package com.vac.vmusic.service.binder;

import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;

import java.util.List;

/**
 * Created by vac on 16/10/28.
 *
 */
public interface IMusicBinder {
    void registerOnPlayMusicStateListener(OnPlayMusicStateListener listener);
    void unRegisterOnPlayMusicStateListener(OnPlayMusicStateListener listener);
    void playNext();
    void playPre();
    void playPause();
    void togglePlay();
    void initToPlay(int position,TingSong tingSong);
    void beginToPlay(int position,TingSong tingSong);
    void seekToSpecifiedPosition(int milliSeconds);
    void setMusicPlayList(List<TingSong> tingSongList, boolean isNeedSave);
    List<TingSong> getMusicPlayList();
    int getCurrentPlayingPosition();
    void changePlayMode();
    int getPlayMode();

    TingSong getCurrentSong();
    int getCurrentState();
}
