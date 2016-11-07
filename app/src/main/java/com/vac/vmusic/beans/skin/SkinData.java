package com.vac.vmusic.beans.skin;

/**
 * Created by vac on 16/11/7.
 *
 */
public class SkinData{
    private long id;
    private String picUrl;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}