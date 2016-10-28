package com.vac.vmusic.beans;

import android.os.IBinder;


/**
 * Created by vac on 16/10/25.
 *
 */
public class BinderSingleton {
    private IBinder musicBinder;
    private static BinderSingleton binderSingleton;
    private BinderSingleton(){}

    public static BinderSingleton getInstance(){
        if (binderSingleton==null){
            binderSingleton = new BinderSingleton();
        }
        return binderSingleton;
    }

    public IBinder getMusicBinder() {
        return musicBinder;
    }

    public void setMusicBinder(IBinder musicBinder) {
        this.musicBinder = musicBinder;
    }
}
