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
        aCache = ACache.get(Constants.ROOT_PATH,Constants.CHILD_LYRIC);
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

    public void saveLyric(TingSong tingSong,String lyricContent){
        aCache.put(ACacheHelperKey.getLyricKey(tingSong),lyricContent);
    }
    public String getLyric(TingSong tingSong){
        return aCache.getAsString(ACacheHelperKey.getLyricKey(tingSong));
    }


    private static class ACacheHelperKey{
        static String getLyricKey(TingSong tingSong){
            return tingSong.getName()+"-"+tingSong.getSingerName()+"-"+tingSong.getSongId()+"-0";
        }
    }
}
