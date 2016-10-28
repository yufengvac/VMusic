package com.vac.vmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.vac.vmusic.application.App;

/**
 * Created by vac on 16/10/28.
 *
 */
public class PreferHelper {
    private static Context context = App.getContext();
    private static SharedPreferences sharedPreferences;
    private static int MODE = Context.MODE_PRIVATE;


    private static class MUSIC_PRE{
        private static final String MUSIC_POSITION = "music";
    }

    public static void saveLastPlayingPosition(int position){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.class.getSimpleName(),MODE);
        sharedPreferences.edit().putInt(MUSIC_PRE.MUSIC_POSITION,position).apply();
    }

    public static int getLastPlayingPosition(){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.class.getSimpleName(),MODE);
        return sharedPreferences.getInt(MUSIC_PRE.MUSIC_POSITION,-1);
    }
}
