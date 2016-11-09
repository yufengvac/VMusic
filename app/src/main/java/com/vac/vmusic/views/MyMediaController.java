package com.vac.vmusic.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.vac.vmusic.R;

import io.vov.vitamio.widget.MediaController;

/**
 * Created by vac on 16/11/9.
 *
 */
public class MyMediaController extends MediaController{
    private Context mContext;
    public MyMediaController(Context context){
        super(context);
        mContext = context;
    }

    @Override
    protected View makeControllerView() {

        return LayoutInflater.from(mContext).inflate(R.layout.my_media_controller,null);
    }
}
