package com.vac.vmusic.beans.discover;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by vac on 16/11/8.
 *
 */
public class DiscoverColumn<T> {
    private long id;
    private String name;
    private String desc;
    private int style;
    private int isNameDisplay;
    private DiscoverAction action;
    private List<T> data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getIsNameDisplay() {
        return isNameDisplay;
    }

    public void setIsNameDisplay(int isNameDisplay) {
        this.isNameDisplay = isNameDisplay;
    }

    public DiscoverAction getAction() {
        return action;
    }

    public void setAction(DiscoverAction action) {
        this.action = action;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DiscoverColumn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", style=" + style +
                ", isNameDisplay=" + isNameDisplay +
                ", action=" + action +
                ", data=" + data +
                '}';
    }
}
