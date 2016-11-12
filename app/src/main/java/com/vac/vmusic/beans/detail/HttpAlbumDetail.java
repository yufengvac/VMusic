package com.vac.vmusic.beans.detail;


/**
 * Created by vac on 16/11/12.
 *
 */
public class HttpAlbumDetail {
    private int code;
    private String msg;
    private int pageCount;
    private int totalCount;
    private int page;
    private int size;
    private AlbumDetail data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AlbumDetail getData() {
        return data;
    }

    public void setData(AlbumDetail data) {
        this.data = data;
    }

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
