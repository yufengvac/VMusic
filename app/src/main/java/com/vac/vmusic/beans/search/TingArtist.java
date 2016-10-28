package com.vac.vmusic.beans.search;

/**
 * Created by vac on 16/10/27.
 *
 */
public class TingArtist {
    private long _id;
    private String singer_name;
    private String alias_name;
    private int song_num;
    private int album_num;
    private String pic_url;
    private long live_id;
    private long shop_id;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public int getSong_num() {
        return song_num;
    }

    public void setSong_num(int song_num) {
        this.song_num = song_num;
    }

    public int getAlbum_num() {
        return album_num;
    }

    public void setAlbum_num(int album_num) {
        this.album_num = album_num;
    }

    public long getLive_id() {
        return live_id;
    }

    public void setLive_id(long live_id) {
        this.live_id = live_id;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }
}
