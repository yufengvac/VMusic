package com.vac.vmusic.callback;

import com.vac.vmusic.beans.LocalMusic;

import java.util.List;

/**
 * Created by vac on 16/11/5.
 */
public interface OnLocalMusicLoadListener {
    void onLocalMusicLoadListener(List<LocalMusic> localMusics);
}
