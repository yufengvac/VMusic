package com.vac.vmusic.search.view;

import android.widget.EditText;

/**
 * Created by vac on 16/10/24.
 *
 */
public interface ISearchMainFragment {
    String getKeyWord();
    void setHotSearch();
    void setNormalSearch();
    EditText getEditText();

    void showClearIcon();
    void closeClearIcon();
}
