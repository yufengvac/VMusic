package com.vac.vmusic.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by vac on 16/8/12.
 *
 */
@SuppressWarnings("NewApi")
public class AlphaUtil {
    private Drawable hideDrawable,showDrawable;
    private OnAlphaCompletedListener onAlphaCompletedListener;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==100){
                float scale = msg.arg1/10000f;
                if (scale<0||scale>1){
                    return;
                }
                showDrawable.setAlpha((int) (255 * scale));
                hideDrawable.setAlpha((int) (255 * (1 - scale)));
//                Log.e("TAG","1="+(int) (255 * scale)+",2="+(int) (255 * (1 - scale)));
//                    Log.e("TAG","showImageView="+showDrawable.getAlpha()+",hideImageView="+hideDrawable.getAlpha());
            }else if (msg.what==101){
//                onAlphaCompletedListener.onAlphaCompleted();
//                showDrawable.setAlpha(255);
//                hideDrawable.setAlpha(0);
            }
        }
    };
    public AlphaUtil(Drawable drawable, Drawable drawable1){
        this.hideDrawable = drawable;
        this.showDrawable = drawable1;
    }

    public void toExecute(){
        new Thread() {
            @Override
            public void run() {
                for (int i=0;i<50;i+=1){
                    final float scale = i*i/(49f*49f);
                    Message msg = Message.obtain();
                    msg.what =100;
                    msg.arg1 = (int) (scale*10000);
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(20);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Message msg = Message.obtain();
                msg.what =101;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    public interface OnAlphaCompletedListener{
        void onAlphaCompleted();
    }

}
