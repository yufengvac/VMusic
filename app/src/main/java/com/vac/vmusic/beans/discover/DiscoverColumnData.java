package com.vac.vmusic.beans.discover;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vac.vmusic.beans.search.TingSong;

import java.util.List;

/**
 * Created by vac on 16/11/8.
 *
 */
@JsonIgnoreProperties(value = {"albumRightKey","status","isExclusive"})
public class DiscoverColumnData {
    private long id;
    private String name;
    private String picUrl;
    private DiscoverAction action;
    private String desc;
    private String author;
    private int listenCount;
    private int comments;
    private int down;
    private int up;
    private int bulletCount;
    private List<TingSong> songs;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public DiscoverAction getAction() {
        return action;
    }

    public void setAction(DiscoverAction action) {
        this.action = action;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getListenCount() {
        return listenCount;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public List<TingSong> getSongs() {
        return songs;
    }

    public void setSongs(List<TingSong> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "DiscoverColumnData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", action=" + action +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", listenCount=" + listenCount +
                ", comments=" + comments +
                ", down=" + down +
                ", up=" + up +
                ", bulletCount=" + bulletCount +
                ", songs=" + songs +
                '}';
    }
}
