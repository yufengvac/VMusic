package com.vac.vmusic.beans.search;

public class TingSongList {

    private String title;
    private String desc;
    private long _id;
    private long quan_id;
    private String song_list;
    private long create_at;
    private int comment_count;
    private int listen_count;
    private String pic_url;
    private String author;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public long getQuan_id() {
        return quan_id;
    }
    public void setQuan_id(long quan_id) {
        this.quan_id = quan_id;
    }
    public String getSong_list() {
        return song_list;
    }
    public void setSong_list(String song_list) {
        this.song_list = song_list;
    }
    public long getCreate_at() {
        return create_at;
    }
    public void setCreate_at(long create_at) {
        this.create_at = create_at;
    }
    public int getComment_count() {
        return comment_count;
    }
    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
    public int getListen_count() {
        return listen_count;
    }
    public void setListen_count(int listen_count) {
        this.listen_count = listen_count;
    }
    public String getPic_url() {
        return pic_url;
    }
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

}