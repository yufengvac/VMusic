package com.vac.vmusic.beans.lyric;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by vac on 16/11/1.
 *
 */
@Root(name = "lrc",strict = false)
public class LyricXml {
    @Attribute
    public String artist;
    @Attribute
    public String title;
    @Attribute
    public String lrcID;
    @Attribute
    public String trc;


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLrcID() {
        return lrcID;
    }

    public void setLrcID(String lrcID) {
        this.lrcID = lrcID;
    }

    public String getTrc() {
        return trc;
    }

    public void setTrc(String trc) {
        this.trc = trc;
    }


    @Override
    public String toString() {
        return "LyricXml{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", lrcID='" + lrcID + '\'' +
                ", trc='" + trc + '\'' +
                '}';
    }
}
