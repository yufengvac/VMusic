package com.vac.vmusic.beans.discover;

/**
 * Created by vac on 16/11/8.
 *
 */
public class DiscoverAction {
    private int type;
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DiscoverAction{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
