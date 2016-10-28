package com.vac.vmusic.homemain.model;

import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.callback.RequestCallback;

/**
 * Created by vac on 16/10/27.
 *
 */
public interface IMainActivityModel {
    void searchArtist(String q, RequestCallback<TingArtist> callback);
}
