package com.vac.vmusic.callback;

import com.vac.vmusic.beans.search.TingSong;

/**
 * Created by vac on 16/10/25.
 *
 */
public interface OnPlayMusicStateListener {

    /**
     * 音乐播放
     */
     void onMusicPlayed(TingSong music);

    /**
     * 音乐暂停
     */
     void onMusicPaused(TingSong music);

    /**
     * 音乐停止
     */
     void onMusicStopped();

    /**
     * 播放模式改变时调用此方法
     *
     * @param playMode
     *            播放模式
     * */
     void onPlayModeChanged(int playMode);

    /**
     * 播放新的音乐
     * @param music tingMusic
     */
     void onNewSongPlayed(TingSong music,int position);

    /**
     * 播放进度
     * @param   percent
     *
     */
     void onPlayProgressUpdate(int percent);
}