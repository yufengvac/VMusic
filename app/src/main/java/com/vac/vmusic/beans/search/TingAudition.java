package com.vac.vmusic.beans.search;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 天天动听的audition资源
 * @author vac
 * create by vac 2016年10月15日14:25:01
 *
 */
public class TingAudition implements Parcelable{

    private int bitRate;//码率 32 128 320
    private int duration;//歌曲时长
    private int size;//歌曲大小
    private String suffix;//歌曲类型 m4a、mp3、..
    private String url;//歌曲下载地址
    private String typeDescription;//歌曲类型说明  流畅品质、标准品质、超高品质

    public int getBitRate() {
        return bitRate;
    }
    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTypeDescription() {
        return typeDescription;
    }
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public TingAudition(){}
    public TingAudition(Parcel arg0) {
        bitRate = arg0.readInt();
        duration = arg0.readInt();
        size = arg0.readInt();
        suffix = arg0.readString();
        url = arg0.readString();
        typeDescription = arg0.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(bitRate);
        arg0.writeInt(duration);
        arg0.writeInt(size);
        arg0.writeString(suffix);
        arg0.writeString(url);
        arg0.writeString(typeDescription);
    }
    public static final Creator<TingAudition> CREATOR = new Creator<TingAudition>() {

        @Override
        public TingAudition createFromParcel(Parcel arg0) {
            return new TingAudition(arg0);
        }

        @Override
        public TingAudition[] newArray(int arg0) {
            return new TingAudition[arg0];
        }
    };

}
