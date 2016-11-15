package com.vac.vmusic.beans.songlist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vac on 16/11/15.
 *
 */
public class SongListDetailImage implements Parcelable{
    private String pic;
    private int source;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public SongListDetailImage(){
    }
    public SongListDetailImage(Parcel parcel){
        pic = parcel.readString();
        source = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pic);
        parcel.writeInt(source);
    }
    public static final Creator<SongListDetailImage> CREATOR = new Creator<SongListDetailImage>() {
        @Override
        public SongListDetailImage createFromParcel(Parcel parcel) {
            return new SongListDetailImage(parcel);
        }

        @Override
        public SongListDetailImage[] newArray(int i) {
            return new SongListDetailImage[i];
        }
    };
}
