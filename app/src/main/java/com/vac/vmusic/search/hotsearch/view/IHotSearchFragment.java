package com.vac.vmusic.search.hotsearch.view;

import android.content.Context;

/**
 * Created by vac on 16/10/24.
 *
 */
public interface IHotSearchFragment {

    Context getMyContext();
    void reloadWordView();
    void showDeleteIcon();
    void hideDeleteIcon();
}
