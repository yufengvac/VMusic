package com.vac.vmusic.beans.search;

import org.litepal.crud.DataSupport;

/**
 * Created by vac on 16/10/27.
 * 播放音乐的类
 */
public class Music extends DataSupport{
    private long songId;
    private String name;
    private String alias;
    private String remarks;
    private String singerName;
    private long albumId;
    private String albumName;
    private int favorites;


}
