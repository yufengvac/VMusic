package com.vac.vmusic.beans.songlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vac.vmusic.beans.search.TingSong;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by vac on 16/11/15.
 *
 */
@JsonIgnoreProperties(value = {"tags"})
public class SongListDetail extends DataSupport implements Parcelable{
    private long songlist_id;
    private List<TingSong> songs;
    private String title;
    private String desc;

    @Column(ignore = true)
    private SongListDetailImage image;
    private Owner owner;
    private int song_count;
    private int favorite_count;
    private String comment_count;
    private int share_count;
    private int listen_count;
    private long created_time;
    private long last_updated;
    private int version;
    private int status;
    private boolean favorite;

    public long getSonglist_id() {
        return songlist_id;
    }

    public void setSonglist_id(long songlist_id) {
        this.songlist_id = songlist_id;
    }

    public List<TingSong> getSongs() {
        return songs;
    }

    public void setSongs(List<TingSong> songs) {
        this.songs = songs;
    }

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

    public SongListDetailImage getImage() {
        return image;
    }

    public void setImage(SongListDetailImage image) {
        this.image = image;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getSong_count() {
        return song_count;
    }

    public void setSong_count(int song_count) {
        this.song_count = song_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getListen_count() {
        return listen_count;
    }

    public void setListen_count(int listen_count) {
        this.listen_count = listen_count;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(long last_updated) {
        this.last_updated = last_updated;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public SongListDetail(){
    }
    public SongListDetail(Parcel parcel){
        songlist_id = parcel.readLong();
        title = parcel.readString();
        desc = parcel.readString();
        image = parcel.readParcelable(SongListDetailImage.class.getClassLoader());
        owner = parcel.readParcelable(Owner.class.getClassLoader());
        song_count = parcel.readInt();
        favorite_count = parcel.readInt();
        comment_count = parcel.readString();
        share_count = parcel.readInt();
        listen_count = parcel.readInt();
        created_time = parcel.readLong();
        last_updated = parcel.readLong();
        version = parcel.readInt();
        status = parcel.readInt();
        Parcelable[] songArray = parcel.readParcelableArray(TingSong.class.getClassLoader());
        for (int i=0;i<songArray.length;i++){
            songs.add((TingSong) songArray[i]);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeLong(songlist_id);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeParcelable(image,0);
        parcel.writeParcelable(owner,0);
        parcel.writeInt(song_count);
        parcel.writeInt(favorite_count);
        parcel.writeString(comment_count);
        parcel.writeInt(share_count);
        parcel.writeInt(listen_count);
        parcel.writeLong(created_time);
        parcel.writeLong(last_updated);
        parcel.writeInt(version);
        parcel.writeInt(status);
    }

    public static final Creator<SongListDetail> CREATOR = new Creator<SongListDetail>() {
        @Override
        public SongListDetail createFromParcel(Parcel parcel) {
            return new SongListDetail(parcel);
        }

        @Override
        public SongListDetail[] newArray(int i) {
            return new SongListDetail[i];
        }
    };
}
