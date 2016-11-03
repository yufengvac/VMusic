package com.vac.vmusic.downloadmanager;

/**
 * Created by vac on 16/11/3.
 *
 */
import android.content.Context;
public class DownLoadManagerFactory {

    private static DownLoadManager mDownLoadM = null;
    private DownLoadManagerFactory(){};
    public static DownLoadManager getInstance(Context context){
        if (mDownLoadM==null) {
            synchronized (DownLoadManager.class){
                if (mDownLoadM==null){
                    mDownLoadM = new DownLoadManager(context);
                }
            }
        }
        return mDownLoadM;
    }
}
