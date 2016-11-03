package com.vac.vmusic.downloadmanager;

/**
 * Created by vac on 16/11/3.\
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 类功能描述：下载器后台服务</br>
 *
 */

public class DownLoadService extends Service {
    private static DownLoadManager  downLoadManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static DownLoadManager getDownLoadManager(){
        return downLoadManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downLoadManager = new DownLoadManager(DownLoadService.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放downLoadManager
        downLoadManager.stopAllTask();
        downLoadManager = null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if(downLoadManager == null){
            downLoadManager = new DownLoadManager(DownLoadService.this);
        }
    }

}
