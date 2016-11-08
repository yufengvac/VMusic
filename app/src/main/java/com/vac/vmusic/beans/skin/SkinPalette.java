package com.vac.vmusic.beans.skin;

/**
 * Created by yufengvac on 16/11/7.
 * 从bitmap中取出来的色调
 */
public class SkinPalette {
    private int vibrantSwatchRgb;
    private int lightVibrantSwatchRgb;
    private int darkVibrantSwatchRgb;
    private int mutedSwatchRgb;
    private int lightMutedSwatchRgb;
    private int darkMutedSwatchRgb;

    private String url;

    public int getVibrantSwatchRgb() {
        return vibrantSwatchRgb;
    }

    public void setVibrantSwatchRgb(int vibrantSwatchRgb) {
        this.vibrantSwatchRgb = vibrantSwatchRgb;
    }

    public int getLightVibrantSwatchRgb() {
        return lightVibrantSwatchRgb;
    }

    public void setLightVibrantSwatchRgb(int lightVibrantSwatchRgb) {
        this.lightVibrantSwatchRgb = lightVibrantSwatchRgb;
    }

    public int getDarkVibrantSwatchRgb() {
        return darkVibrantSwatchRgb;
    }

    public void setDarkVibrantSwatchRgb(int darkVibrantSwatchRgb) {
        this.darkVibrantSwatchRgb = darkVibrantSwatchRgb;
    }

    public int getMutedSwatchRgb() {
        return mutedSwatchRgb;
    }

    public void setMutedSwatchRgb(int mutedSwatchRgb) {
        this.mutedSwatchRgb = mutedSwatchRgb;
    }

    public int getLightMutedSwatchRgb() {
        return lightMutedSwatchRgb;
    }

    public void setLightMutedSwatchRgb(int lightMutedSwatchRgb) {
        this.lightMutedSwatchRgb = lightMutedSwatchRgb;
    }

    public int getDarkMutedSwatchRgb() {
        return darkMutedSwatchRgb;
    }

    public void setDarkMutedSwatchRgb(int darkMutedSwatchRgb) {
        this.darkMutedSwatchRgb = darkMutedSwatchRgb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
