package com.vac.vmusic.playmusic.view;

import android.content.Context;

import com.vac.vmusic.beans.search.TingSong;

/**
 * Created by vac on 16/10/29.
 *
 */
public interface IPlayMusicActivity {
    void back();
    void initMusicInfo(TingSong tingSong);
    Context getPlayMusicContext();
}
