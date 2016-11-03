package com.vac.vmusic.beans;

import android.os.Parcel;

import com.vac.vmusic.beans.search.TingSong;

/**
 * Created by vac on 16/11/3.
 *
 */
public class LocalMusic extends TingSong {
    private String title;
    private String data;
    private int duration;
    private int size;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
    @Override
    public int describeContents() {
        return 0;
    }
    public LocalMusic(){
    }
    public LocalMusic(Parcel parcel){
        title = parcel.readString();
        data = parcel.readString();
        size = parcel.readInt();
        duration = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(title);
        arg0.writeString(data);
        arg0.writeInt(size);
        arg0.writeInt(duration);
    }

    public static final Creator<LocalMusic> CREATOR = new Creator<LocalMusic>() {

        @Override
        public LocalMusic createFromParcel(Parcel arg0) {
            return new LocalMusic(arg0);
        }

        @Override
        public LocalMusic[] newArray(int arg0) {
            return new LocalMusic[arg0];
        }
    };

    @Override
    public String toString() {
        return "LocalMusic{" +
                ", title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                '}';
    }
}
