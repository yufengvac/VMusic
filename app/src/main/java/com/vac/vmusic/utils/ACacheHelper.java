package com.vac.vmusic.utils;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.vac.vmusic.beans.search.TingSong;

import org.json.JSONArray;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.List;

/**
 * Created by vac on 16/10/27.
 *
 */
public class ACacheHelper  {
    private static ACache aCache;
    private static ACacheHelper aCacheHelper = null;
    private ACacheHelper(){
        aCache = ACache.get(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator+"vmusic","tempCache");

    }

    public static ACacheHelper getInstance(){
        if(aCacheHelper==null){
            synchronized (ACacheHelper.class){
                if (aCacheHelper==null){
                    aCacheHelper = new ACacheHelper();
                }
            }
        }
        return aCacheHelper;
    }

    public static void saveLastMusicList(List<TingSong> tingSongs){
        DataSupport.saveAll(tingSongs);
    }
    public static List<TingSong> getLastMusicList(){
        return DataSupport.findAll(TingSong.class);
    }



    private static class ACacheHelperKey{
        private static final String LAST_MUSIC_LIST = "last_music_list";
    }
}
