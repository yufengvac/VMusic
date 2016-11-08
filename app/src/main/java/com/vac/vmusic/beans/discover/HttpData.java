package com.vac.vmusic.beans.discover;

import java.util.List;

/**
 * Created by vac on 16/11/8.
 *
 */
public class HttpData<T> {
    private List<T> data;
    private long version;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
