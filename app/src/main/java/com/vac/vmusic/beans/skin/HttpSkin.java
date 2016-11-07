package com.vac.vmusic.beans.skin;

import java.util.List;

/**
 * Created by vac on 16/11/7.
 *
 */
public class HttpSkin<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
