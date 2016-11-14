package com.vac.vmusic.beans.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.vac.vmusic.beans.search.TingSong;
/**
 * Created by vac on 16/11/12.
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(value = {"albumRightKey","status","isExclusive","publisher","companyId","titleSongs"
,"styles","genres","tags","singerSFlag"})
public class AlbumDetail implements Parcelable {
    private long albumId;
    private String name;
    private String description;
    private String singerName;
    private String picUrl;
    private String publishDate;
    private int publishYear;
    private String singerPicUrl;
    private String alias;
    private int type;
    private String typeName;
    private long coverId;
    private String companyName;
    private int commentCount;
    private int favoriteCount;
    private boolean followFlag;
    private long userId;
    private long singerId;

    private String lang;
    private long[] songs;
    private List<TingSong> songList;

    public long getSingerId() {
        return singerId;
    }

    public void setSingerId(long singerId) {
        this.singerId = singerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(boolean followFlag) {
        this.followFlag = followFlag;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getCoverId() {
        return coverId;
    }

    public void setCoverId(long coverId) {
        this.coverId = coverId;
    }

    public int getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    public long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public long[] getSongs() {
        return songs;
    }
    public void setSongs(long[] songs) {
        this.songs = songs;
    }

    public List<TingSong> getSongList() {
        return songList;
    }

    public String getSingerPicUrl() {
        return singerPicUrl;
    }

    public void setSingerPicUrl(String singerPicUrl) {
        this.singerPicUrl = singerPicUrl;
    }

    public void setSongList(List<TingSong> songList) {
        this.songList = songList;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public AlbumDetail(){}
    public AlbumDetail(Parcel parcel){
        albumId = parcel.readLong();
        name = parcel.readString();
        description = parcel.readString();
        singerName = parcel.readString();
        picUrl = parcel.readString();
        publishDate = parcel.readString();
        publishYear = parcel.readInt();
        singerPicUrl = parcel.readString();
        alias = parcel.readString();
        type = parcel.readInt();
        typeName = parcel.readString();
        coverId = parcel.readLong();
        companyName = parcel.readString();
        commentCount = parcel.readInt();
        favoriteCount = parcel.readInt();
        userId = parcel.readLong();
        singerId = parcel.readLong();
        lang = parcel.readString();
        Parcelable[] songs = parcel.readParcelableArray(TingSong.class.getClassLoader());
        for (int i=0;i<songs.length;i++){
            songList.add((TingSong) songs[i]);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(albumId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(singerName);
        parcel.writeString(picUrl);
        parcel.writeString(publishDate);
        parcel.writeInt(publishYear);
        parcel.writeString(singerPicUrl);
        parcel.writeString(alias);
        parcel.writeInt(type);
        parcel.writeString(typeName);
        parcel.writeLong(coverId);
        parcel.writeString(companyName);
        parcel.writeInt(favoriteCount);
        parcel.writeInt(commentCount);
        parcel.writeLong(userId);
        parcel.writeLong(singerId);
        parcel.writeString(lang);
        Parcelable[] par1 = new Parcelable[songList.size()];
        for (int x=0;x<par1.length;x++){
            par1[x] = songList.get(x);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumDetail> CREATOR = new Creator<AlbumDetail>() {
        @Override
        public AlbumDetail createFromParcel(Parcel parcel) {
            return new AlbumDetail(parcel);
        }

        @Override
        public AlbumDetail[] newArray(int i) {
            return new AlbumDetail[i];
        }
    };

    @Override
    public String toString() {
        return "AlbumDetail{" +
                "albumId=" + albumId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", singerName='" + singerName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", publishYear=" + publishYear +
                ", singerPicUrl='" + singerPicUrl + '\'' +
                ", alias='" + alias + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", coverId=" + coverId +
                ", companyName='" + companyName + '\'' +
                ", commentCount=" + commentCount +
                ", favoriteCount=" + favoriteCount +
                ", followFlag=" + followFlag +
                ", userId=" + userId +
                ", singerId=" + singerId +
                ", lang='" + lang + '\'' +
                ", songs=" + Arrays.toString(songs) +
                ", songList=" + songList +
                '}';
    }
}

