package com.vac.vmusic.service.service;

import android.media.MediaPlayer;

import com.vac.vmusic.beans.lyric.LyricSentence;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.utils.LyricLoadHelper;

import java.util.List;

/**
 * Created by vac on 16/10/28.
 *
 */
public interface IService {

    List<OnPlayMusicStateListener> getOnPlayMusicStateListener();

    List<LyricLoadHelper.LyricListener> getOnLyricListener();

    void requestToPlay();

    void setServiceAsForeground(String text);

    void playSong();

    void requestToPause();

    void requestTogglePlay();

    void requestToPlayNext(boolean isUserClick);

    void requestToPlayPrevious(boolean isUserClick);

    void requestToStop();

    void requestToStop(boolean isForce);

    void releaseResource(boolean isReleaseMediaPlayer);

    void createMediaPlayerIfNeed();

    void configAndStartMediaPlayer();

    boolean isNeedUpdate(List<TingSong> tingSongList);

    void updateNotification(String text);

    void updatePlayProcess();

    void setRequestMusicPosition(int position);

    void setRequestPlayMusicId(long id);

    int getPlayerState();

    MediaPlayer getPlayer();

    List<TingSong> getPlayingMusicList();

    int getPlayerMode();

    void setPlayerMode(int playerMode);

    int getPlayingMusicPosition();

    TingSong getCurrentSong();

    void setLyricContent(String lyric);

    List<LyricSentence> getLyricSentenceList();

    TingSong getTingSongById(long songId);

    void setPlayingMusicList(List<? extends TingSong> playingMusicList);
}
