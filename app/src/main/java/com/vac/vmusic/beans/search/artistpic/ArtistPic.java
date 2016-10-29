package com.vac.vmusic.beans.search.artistpic;

/**
 * Created by vac on 16/10/29.
 *
 */
public class ArtistPic<T>{
    private long _id;
    private String name;
    private T[] picUrls;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T[] getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(T[] picUrls) {
        this.picUrls = picUrls;
    }
}
