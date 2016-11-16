package com.vac.vmusic.beans.songlist;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by vac on 16/11/15.
 *
 */
public class Owner extends DataSupport implements Parcelable{
    private long user_id;
    private String nick_name;
    private String tag;
    private int identity;
    private String portrait_pic;
    private String cover_pic;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getPortrait_pic() {
        return portrait_pic;
    }

    public void setPortrait_pic(String portrait_pic) {
        this.portrait_pic = portrait_pic;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public Owner(){
    }
    public Owner(Parcel parcel){
        user_id = parcel.readLong();
        nick_name = parcel.readString();
        tag = parcel.readString();
        identity = parcel.readInt();
        portrait_pic = parcel.readString();
        cover_pic = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(user_id);
        parcel.writeString(nick_name);
        parcel.writeString(tag);
        parcel.writeInt(identity);
        parcel.writeString(portrait_pic);
        parcel.writeString(cover_pic);
    }
    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel parcel) {
            return new Owner(parcel);
        }

        @Override
        public Owner[] newArray(int i) {
            return new Owner[i];
        }
    };
}
