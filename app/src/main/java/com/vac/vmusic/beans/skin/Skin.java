package com.vac.vmusic.beans.skin;

import java.util.List;

/**
 * Created by vac on 16/11/7.
 *
 */
public class Skin {

    private String tagName;
    private List<SkinData> data;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<SkinData> getData() {
        return data;
    }

    public void setData(List<SkinData> data) {
        this.data = data;
    }


}
