package com.vac.vmusic.application;

import android.app.Application;
import android.content.Context;


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
    }

    public static Context getContext(){
        return sApplicationContext;
    }
}
