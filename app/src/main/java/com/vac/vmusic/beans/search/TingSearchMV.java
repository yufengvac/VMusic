package com.vac.vmusic.beans.search;

import java.util.ArrayList;


public class TingSearchMV {

    private long id;
    private long songId;
    private String videoName;
    private String singerName;
    private String picUrl;
    private int pickCount;
    private int bulletCount;
    private ArrayList<TingMV> mvList;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getSongId() {
        return songId;
    }
    public void setSongId(long songId) {
        this.songId = songId;
    }
    public String getVideoName() {
        return videoName;
    }
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    public String getSingerName() {
        return singerName;
    }
    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public int getPickCount() {
        return pickCount;
    }
    public void setPickCount(int pickCount) {
        this.pickCount = pickCount;
    }
    public int getBulletCount() {
        return bulletCount;
    }
    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }
    public ArrayList<TingMV> getMvList() {
        return mvList;
    }
    public void setMvList(ArrayList<TingMV> mvList) {
        this.mvList = mvList;
    }


}