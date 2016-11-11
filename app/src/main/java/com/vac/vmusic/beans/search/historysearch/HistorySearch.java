package com.vac.vmusic.beans.search.historysearch;

import org.litepal.crud.DataSupport;

/**
 * Created by vac on 16/11/11.
 *
 */
public class HistorySearch extends DataSupport{
    private long timeStamp;
    private String content;
    private int count;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
