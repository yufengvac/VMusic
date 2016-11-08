package com.vac.vmusic.beans.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/***
 * 天天动听单曲
 * @author vac
 *
 */
@JsonIgnoreProperties(value = {"mvPickCount","mvBulletCount","outFlag","outList","commentCount","riskRank",
"outLinks","rightKey","loginStatus","musicPackage","albumPackage","promotionPackage","operType","level",
"isExclusive","listenCount","singers","isPlaying"})
public class TingSong extends DataSupport implements Parcelable{

    private long songId;
    private String name;
    private String alias;
    private String remarks;
    private boolean firstHit;
    private long librettistId;//作词者id
    private String librettistName;//作词者姓名
    private long composerId;//作曲者id
    private String composerName;//作曲者姓名
    private long singerId;//歌手id
    private String singerName;//歌手姓名
    private int singerSFlag;
    private long albumId;//专辑id
    private String albumName;//专辑名称
    private int favorites;//喜欢数
    private long originalId;
    private int type;
    private String tags;

    private String picUrl;

    private int releaseYear;
    private int producer;
    private int publisher;
    private int audit;
    private int lang;

    private List<TingAudition> auditionList = new ArrayList<>();

    @Column(ignore = true)
    private int status;



    @Column(ignore = true)
    private ArrayList<TingMV> mvList;

//    @Column(ignore = true)
    private ArrayList<TingAudition> urlList = new ArrayList<>();

//    @Column(ignore = true)
    private ArrayList<TingAudition> llList = new ArrayList<>();

    @Column(ignore = true)
    private boolean isPlaying;

    @Column(ignore = true)
    private boolean isFavored;


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isFavored() {
        return isFavored;
    }

    public void setFavored(boolean favored) {
        isFavored = favored;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getProducer() {
        return producer;
    }

    public void setProducer(int producer) {
        this.producer = producer;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public long getSongId() {
        return songId;
    }
    public void setSongId(long songId) {
        this.songId = songId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public boolean isFirstHit() {
        return firstHit;
    }
    public void setFirstHit(boolean firstHit) {
        this.firstHit = firstHit;
    }
    public long getLibrettistId() {
        return librettistId;
    }
    public void setLibrettistId(long librettistId) {
        this.librettistId = librettistId;
    }
    public String getLibrettistName() {
        return librettistName;
    }
    public void setLibrettistName(String librettistName) {
        this.librettistName = librettistName;
    }
    public long getComposerId() {
        return composerId;
    }
    public void setComposerId(long composerId) {
        this.composerId = composerId;
    }
    public String getComposerName() {
        return composerName;
    }
    public void setComposerName(String composerName) {
        this.composerName = composerName;
    }
    public long getSingerId() {
        return singerId;
    }
    public void setSingerId(long singerId) {
        this.singerId = singerId;
    }
    public String getSingerName() {
        return singerName;
    }
    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
    public int getSingerSFlag() {
        return singerSFlag;
    }
    public void setSingerSFlag(int singerSFlag) {
        this.singerSFlag = singerSFlag;
    }
    public long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public int getFavorites() {
        return favorites;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
    public long getOriginalId() {
        return originalId;
    }
    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tag) {
        this.tags = tag;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public List<TingAudition> getAuditionList() {
        return auditionList;
    }
    public void setAuditionList(List<TingAudition> auditionList) {
        this.auditionList = auditionList;
    }
    public ArrayList<TingMV> getMvList() {
        return mvList;
    }
    public void setMvList(ArrayList<TingMV> mvList) {
        this.mvList = mvList;
    }

    public ArrayList<TingAudition> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<TingAudition> urlList) {
        this.urlList = urlList;
    }

    public ArrayList<TingAudition> getLlList() {
        return llList;
    }

    public void setLlList(ArrayList<TingAudition> llList) {
        this.llList = llList;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public TingSong(){
    }

    public TingSong(Parcel arg0){
        songId = arg0.readLong();
        name = arg0.readString();
        alias = arg0.readString();
        remarks= arg0.readString();
        librettistId = arg0.readLong();
        librettistName = arg0.readString();
        composerId = arg0.readLong();
        composerName = arg0.readString();
        singerId = arg0.readLong();
        singerName = arg0.readString();
        singerSFlag = arg0.readInt();
        albumId = arg0.readLong();
        albumName = arg0.readString();
        favorites =arg0.readInt();
        originalId = arg0.readLong();
        type = arg0.readInt();
        tags = arg0.readString();
        status = arg0.readInt();
        picUrl = arg0.readString();
        Parcelable[] tingAudition = arg0.readParcelableArray(TingAudition.class.getClassLoader());
        for (int i = 0; i < tingAudition.length; i++) {
            auditionList.add((TingAudition)tingAudition[i]);
        }

        Parcelable[] tingMv = arg0.readParcelableArray(TingMV.class.getClassLoader());
        for (int i = 0; i < tingMv.length; i++) {
            mvList.add((TingMV)tingMv[i]);
        }

        Parcelable[] tingUrl = arg0.readParcelableArray(TingAudition.class.getClassLoader());
        for (int i = 0; i < tingUrl.length; i++) {
            urlList.add((TingAudition)tingUrl[i]);
        }
        Parcelable[] tingll_ = arg0.readParcelableArray(TingAudition.class.getClassLoader());
        for (int i = 0; i < tingll_.length; i++) {
            llList.add((TingAudition) tingll_[i]);
        }
    }
    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeLong(songId);
        arg0.writeString(name);
        arg0.writeString(alias);
        arg0.writeString(remarks);
        arg0.writeLong(librettistId);
        arg0.writeString(librettistName);
        arg0.writeLong(composerId);
        arg0.writeString(composerName);
        arg0.writeLong(singerId);
        arg0.writeString(singerName);
        arg0.writeInt(singerSFlag);
        arg0.writeLong(albumId);
        arg0.writeString(albumName);
        arg0.writeInt(favorites);
        arg0.writeLong(originalId);
        arg0.writeInt(type);
        arg0.writeString(tags);
        arg0.writeInt(status);
        arg0.writeString(picUrl);
//		arg0.writeList(auditionList);
//		arg0.writeList(mvList);
//		arg0.writeList(outList);

        Parcelable[] par1 = new Parcelable[auditionList.size()];
        for (int i = 0; i < par1.length; i++) {
            par1[i]  = auditionList.get(i);
        }

        Parcelable[] par2 = new Parcelable[mvList.size()];
        for (int i = 0; i < par2.length; i++) {
            par2[i]  = mvList.get(i);
        }

        Parcelable[] par3 = new Parcelable[urlList.size()];
        for (int i = 0; i < par3.length; i++) {
            par3[i]  = urlList.get(i);
        }

        Parcelable[] par4 = new Parcelable[llList.size()];
        for (int i = 0; i < par4.length; i++) {
            par4[i]  = llList.get(i);
        }
    }
    public static final Creator<TingSong> CREATOR = new Creator<TingSong>() {

        @Override
        public TingSong createFromParcel(Parcel arg0) {
            return new TingSong(arg0);
        }

        @Override
        public TingSong[] newArray(int arg0) {
            return new TingSong[arg0];
        }
    };

    @Override
    public String toString() {
        return "TingSong{" +
                "songId=" + songId +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", remarks='" + remarks + '\'' +
                ", firstHit=" + firstHit +
                ", librettistId=" + librettistId +
                ", librettistName='" + librettistName + '\'' +
                ", composerId=" + composerId +
                ", composerName='" + composerName + '\'' +
                ", singerId=" + singerId +
                ", singerName='" + singerName + '\'' +
                ", singerSFlag=" + singerSFlag +
                ", albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", favorites=" + favorites +
                ", originalId=" + originalId +
                ", type=" + type +
                ", tags='" + tags + '\'' +
                ", releasYear=" + releaseYear +
                ", producer=" + producer +
                ", publisher=" + publisher +
                ", audit=" + audit +
                ", lang=" + lang +
                ", status=" + status +
                ", auditionList=" + auditionList +
                ", mvList=" + mvList +
                ", picUrl=" + picUrl +
                '}';
    }
}
