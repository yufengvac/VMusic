package com.vac.vmusic.utils;

import android.graphics.Color;
import android.util.Log;

import com.vac.vmusic.beans.HomeColor;

/**
 * Created by vac on 16/10/22.
 * 主页的颜色管理类
 */
public class HomeColorManager {

    private static final int BASEHEIGHT = 300;
    private HomeColor homeColor;
    private float scale;

    private int currentAlpha,currentRed,currentGreen,currentBlue;
    public HomeColorManager(){
        homeColor = new HomeColor();
        homeColor.setAlpha(0xff);
        homeColor.setRed(0xff);
        homeColor.setGreen(0x55);
        homeColor.setBlue(0x0);

        currentAlpha = homeColor.getAlpha();
        currentRed = homeColor.getRed();
        currentGreen = homeColor.getGreen();
        currentBlue = homeColor.getBlue();
    }
    public int transferColorByScroll(int scrollY){
        if (scrollY<=BASEHEIGHT&&scrollY>0){
            scale = scrollY*1f/BASEHEIGHT;
            currentAlpha = 0x30+(int)((0xff-0x30)*scale);
            currentRed = (int)(Math.abs(homeColor.getRed()-0x10)*scale);
            currentGreen = (int)(Math.abs(homeColor.getGreen()-0x10)*scale);
            currentBlue = (int)(Math.abs(homeColor.getBlue()-0x10)*scale);
        }else if (scrollY>BASEHEIGHT){
            scale = 1;
            currentAlpha = 0xff;
            currentRed = Math.abs(homeColor.getRed()-0x10);
            currentGreen = Math.abs(homeColor.getGreen()-0x10);
            currentBlue = Math.abs(homeColor.getBlue()-0x10);

        }else if (scrollY==0){
            scale = 0;
            currentAlpha = 0x30;
            currentRed = currentGreen= currentBlue = 0x0;
        }
        return Color.argb(currentAlpha,currentRed,currentGreen,currentBlue);
    }

    public int transferColorByPagerOffset(float positionOffset){
        int a = (int) (currentAlpha + (0xff - currentAlpha) * positionOffset);
        int r = (int) (currentRed +(Math.abs(homeColor.getRed() - 0x10 )-currentRed) * positionOffset);
        int g = (int) (currentGreen+(Math.abs(homeColor.getGreen() - 0x10)-currentGreen )* positionOffset);
        int b =  (int) (currentBlue +(Math.abs(homeColor.getBlue() - 0x10) - currentBlue) * positionOffset);
        return Color.argb(a, r, g, b);
    }

    public int getCurrentColor(){
        return Color.argb(currentAlpha,currentRed,currentGreen,currentBlue);
    }

    public float getScale(){
        return scale;
    }
}
