package com.vac.vmusic.skinfragment.fragments.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vac.vmusic.beans.skin.SkinPalette;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;

import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yufengvac on 16/11/7.
 *
 */
public class SkinFragmentModel {
   private Bitmap bitmap ;
    public void updateColor(final Context context, final String url){
        Observable.create(new Observable.OnSubscribe<SkinPalette>() {
            @Override
            public void call(final Subscriber<? super SkinPalette> subscriber) {
                try {
                    bitmap = Glide.with(context).load(url).asBitmap().fitCenter().into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch vibrantSwatch =  palette.getVibrantSwatch();
                            Palette.Swatch lightVibrantSwatch =  palette.getLightVibrantSwatch();
                            Palette.Swatch darkVibrantSwatch =  palette.getDarkVibrantSwatch();
                            Palette.Swatch mutedSwatch =  palette.getMutedSwatch();
                            Palette.Swatch lightMutedSwatch =  palette.getLightMutedSwatch();
                            Palette.Swatch darkMutedSwatch =  palette.getDarkMutedSwatch();

                            SkinPalette skinPalette = new SkinPalette();
//                            skinPalette.setVibrantSwatchRgb(vibrantSwatch!=null?vibrantSwatch.getRgb():-1);
//                            skinPalette.setLightMutedSwatchRgb(lightVibrantSwatch!=null?lightVibrantSwatch.getRgb():-1);
//                            skinPalette.setDarkVibrantSwatchRgb(darkVibrantSwatch!=null?darkVibrantSwatch.getRgb():-1);
//                            skinPalette.setMutedSwatchRgb(mutedSwatch!=null?mutedSwatch.getRgb():-1);
//                            skinPalette.setLightMutedSwatchRgb(lightMutedSwatch!=null?lightMutedSwatch.getRgb():-1);
//                            skinPalette.setDarkMutedSwatchRgb(darkMutedSwatch!=null?darkMutedSwatch.getRgb():-1);

                            skinPalette.setVibrantSwatchRgb(palette.getVibrantColor(Color.WHITE));
                            skinPalette.setLightMutedSwatchRgb(palette.getLightVibrantColor(Color.WHITE));
                            skinPalette.setDarkVibrantSwatchRgb(palette.getDarkVibrantColor(Color.WHITE));
                            skinPalette.setMutedSwatchRgb(palette.getMutedColor(Color.WHITE));
                            skinPalette.setLightMutedSwatchRgb(palette.getLightMutedColor(Color.WHITE));
                            skinPalette.setDarkMutedSwatchRgb(palette.getDarkMutedColor(Color.WHITE));


                            subscriber.onNext(skinPalette);

//                            if (vibrantSwatch!=null&&lightVibrantSwatch!=null&&darkVibrantSwatch!=null&&
//                                    mutedSwatch!=null&&lightMutedSwatch!=null&&darkMutedSwatch!=null){
//                                int rgb = vibrantSwatch.getRgb();
//                                Log.i("SkinFragment","充满活力的色调是:"+rgb+",亮色调:"+lightVibrantSwatch.getRgb()
//                                        +",暗色调:"+darkVibrantSwatch.getRgb()+"----"
//                                        +",柔和 色调:"+mutedSwatch.getRgb()+",柔和的 亮色调:"+lightMutedSwatch.getRgb()
//                                        +",柔和的 暗色调:"+darkMutedSwatch.getRgb());
//                            }
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SkinPalette>() {
                    @Override
                    public void onCompleted() {
                        if (bitmap!=null){
                            bitmap.recycle();
                            bitmap = null;
                            System.gc();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SkinPalette skinPalette) {
                        HomeColorManager homeColorManager = new HomeColorManager();
                        if (skinPalette.getDarkVibrantSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getDarkVibrantSwatchRgb());
                        }else if (skinPalette.getLightVibrantSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getLightVibrantSwatchRgb());
                        }else if (skinPalette.getVibrantSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getVibrantSwatchRgb());
                        }else if (skinPalette.getLightMutedSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getLightMutedSwatchRgb());
                        }else if (skinPalette.getDarkMutedSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getDarkMutedSwatchRgb());
                        }else if (skinPalette.getMutedSwatchRgb()!=Color.WHITE){
                            homeColorManager.setCurrentColor(skinPalette.getMutedSwatchRgb());
                        }

                        RxBus.get().post("color",skinPalette);
                    }
                });
    }
}
