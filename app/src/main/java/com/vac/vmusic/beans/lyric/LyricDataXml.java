package com.vac.vmusic.beans.lyric;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/1.
 *
 */
@Root(name = "lrc_list",strict = true)
public class LyricDataXml {
    @ElementList(required = true,inline = true,entry = "lrc")
    public List<LyricXml> lyricXmls = new ArrayList<>();

    public List<LyricXml> getLyricXmls() {
        return lyricXmls;
    }

    public void setLyricXmls(List<LyricXml> lyricXmls) {
        this.lyricXmls = lyricXmls;
    }
}
