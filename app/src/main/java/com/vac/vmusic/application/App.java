package com.vac.vmusic.application;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;


/**
 * Created by vac on 16/10/15.
 * application
 */
public class App extends Application {
    private static Context sApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
        LitePalApplication.initialize(this);
    }

    public static Context getContext(){
        return sApplicationContext;
    }
}
