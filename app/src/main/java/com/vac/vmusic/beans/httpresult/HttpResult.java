package com.vac.vmusic.beans.httpresult;

import java.util.List;

/**
 * Created by vac on 16/10/15.
 * 接口返回的统一Json
 */
public class HttpResult<T> {
    private int pageCount;
    private int totalCount;
    private List<T> data;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "pageCount=" + pageCount +
                ", totalCount=" + totalCount +
                ", data=" + data +
                '}';
    }
}
