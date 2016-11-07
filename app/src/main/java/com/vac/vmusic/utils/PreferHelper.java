package com.vac.vmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

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
        private static final String BASE_COLOR = "color";
    }

    public static void saveLastPlayingPosition(int position){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.class.getSimpleName(),MODE);
        sharedPreferences.edit().putInt(MUSIC_PRE.MUSIC_POSITION,position).apply();
    }

    public static int getLastPlayingPosition(){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.class.getSimpleName(),MODE);
        return sharedPreferences.getInt(MUSIC_PRE.MUSIC_POSITION,-1);
    }

    public static void saveLastColor(int red,int green,int blue){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.BASE_COLOR,MODE);
        sharedPreferences.edit().putInt("red",red).putInt("green",green).putInt("blue",blue).apply();
    }

    public static int getLastColor(){
        sharedPreferences = context.getSharedPreferences(MUSIC_PRE.BASE_COLOR,MODE);
        return Color.argb(0xff,sharedPreferences.getInt("red",0x00),
                sharedPreferences.getInt("green",0x00),sharedPreferences.getInt("blue",0x00));
    }
}
