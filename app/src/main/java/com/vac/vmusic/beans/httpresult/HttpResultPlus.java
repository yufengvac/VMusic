package com.vac.vmusic.beans.httpresult;

import java.util.List;

/**
 * Created by vac on 16/10/15.
 *
 */
public class HttpResultPlus<T> {
    private int code;
    private int rows;
    private int pages;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
