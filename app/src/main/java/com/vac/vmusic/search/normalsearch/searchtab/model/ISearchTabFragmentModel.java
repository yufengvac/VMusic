package com.vac.vmusic.search.normalsearch.searchtab.model;


import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by vac on 16/10/18.
 *
 */
public interface ISearchTabFragmentModel {
    Subscription searchSong(int size, int page, String q, RequestCallback<TingSong> callback);
    Subscription searchAlbum(int size, int page, String q, RequestCallback<TingAlbum> callback);
    Subscription searchSongList(int size, int page, String q, RequestCallback<TingSongList> callback);
    Subscription searchMV(int size, int page, String q, RequestCallback<TingSearchMV> callback);

}
