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
    private  static HomeColor homeColor;
    private float scale;

    private static int currentAlpha=0,currentRed=0,currentGreen=0,currentBlue=0;
    private static HomeColorManager homeColorManager;
    private HomeColorManager(){
        if (homeColor==null){
            homeColor = new HomeColor();
//            homeColor.setAlpha(0xff);
//            int color = PreferHelper.getLastColor();
//            homeColor.setRed((color & 0xff0000) >> 16);
//            homeColor.setGreen((color&0x00ff00) >> 8);
//            homeColor.setBlue(color & 0x0000ff);
        }

        if (currentAlpha==0&&currentRed==0&&currentGreen==0&&currentBlue==0){
            setCurrentColor(PreferHelper.getLastColor());
        }
    }
    public static HomeColorManager getHomeColorManager(){
        if (homeColorManager==null){
            synchronized (HomeColorManager.class){
                if (homeColorManager==null){
                    homeColorManager = new HomeColorManager();
                }
            }
        }
        return homeColorManager;
    }

    public void setCurrentColor(int color){
        currentAlpha = 0xff;
        currentRed = (color & 0xff0000) >> 16;
        currentGreen = (color&0x00ff00) >> 8;
        currentBlue = (color & 0x0000ff);
        PreferHelper.saveLastColor(currentRed,currentGreen,currentBlue);
        homeColor.setAlpha(currentAlpha);
        homeColor.setRed(currentRed);
        homeColor.setGreen(currentGreen);
        homeColor.setBlue(currentBlue);
    }

    public int transferColorByScrollWithNoInitColor(int scrollY){
        if (scrollY<=BASEHEIGHT&&scrollY>30){
            scale = scrollY*1f/BASEHEIGHT;
            currentAlpha = (int)(0xff*scale);
            currentRed = (int)(Math.abs(homeColor.getRed()-0x10)*scale);
            currentGreen = (int)(Math.abs(homeColor.getGreen()-0x10)*scale);
            currentBlue = (int)(Math.abs(homeColor.getBlue()-0x10)*scale);
        }else if (scrollY>BASEHEIGHT){
            scale = 1;
            currentAlpha = 0xff;
            currentRed = Math.abs(homeColor.getRed()-0x10);
            currentGreen = Math.abs(homeColor.getGreen()-0x10);
            currentBlue = Math.abs(homeColor.getBlue()-0x10);

        }else if (scrollY<=30){
            scale = 0;
            currentAlpha = 0x0;
            currentRed = currentGreen= currentBlue = 0x0;
        }
        return Color.argb(currentAlpha,currentRed,currentGreen,currentBlue);
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
        return Color.argb(0xff,homeColor.getRed(),homeColor.getGreen(),homeColor.getBlue());
    }

    public int getCurrentLightColor(){
        return Color.argb(0xaa,homeColor.getRed(),homeColor.getGreen(),homeColor.getBlue());
    }

    public float getScale(){
        return scale;
    }
}
