package com.vac.vmusic.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by vac on 16/10/31.
 *
 */
public class Constants {
    public final static String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator
    +"vmusic"+File.separator;

    public final static String CHILD_HTTP_CACHE = "httpCache";

    public final static String CHILD_ARTIST_PIC = "photoGraphic";

    public final static String CHILD_LYRIC = "lyric";
}
