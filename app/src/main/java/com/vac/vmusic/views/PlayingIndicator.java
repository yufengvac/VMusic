package com.vac.vmusic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vac on 16/11/10.
 *
 */
public class PlayingIndicator extends View{
    private long timeInterval = 120;
    private boolean isAnimation = true;

    private RectF rectF1,rectF2,rectF3,rectF4;
    private Paint paint;
    private int defaultColor = Color.argb(255,48,63,159);
    private int horizonSpacing= 5;
    private int rectfWidth = 5;

    private int maxLength = 40;
    private int initLength1 = 15,initLength2 = maxLength,initLength3=30,initLength4=9;

    private int moveLength1 = 12,moveLength2 = 30,moveLength3 = 27,moveLength4 = 18;

    private int count = 0,count1 = 0;
    public PlayingIndicator(Context context){
        super(context);
        init();
    }

    public PlayingIndicator(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }
    public PlayingIndicator(Context context, AttributeSet attributeSet,int defType){
        super(context,attributeSet,defType);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(defaultColor);
        paint.setAntiAlias(true);

        rectF1 = new RectF(0,maxLength-initLength1,rectfWidth,maxLength);
        rectF2 = new RectF(rectfWidth+horizonSpacing,maxLength-initLength2,rectfWidth*2+horizonSpacing,maxLength);
        rectF3 = new RectF(rectfWidth*2+horizonSpacing*2,maxLength-initLength3,rectfWidth*3+horizonSpacing*2,maxLength);
        rectF4 = new RectF(rectfWidth*3+horizonSpacing*3,maxLength-initLength4,rectfWidth*4+horizonSpacing*3,maxLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectF1,paint);
        canvas.drawRect(rectF2,paint);
        canvas.drawRect(rectF3,paint);
        canvas.drawRect(rectF4,paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        isAnimation = true;
//        if (!playingThread.isAlive()){
//            playingThread.start();
//        }
//        if (!playingThreadSlow.isAlive()){
//            playingThreadSlow.start();
//        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAnimation = false;
    }


    private Thread playingThread = new Thread(){
        @Override
        public void run() {
            while (isAnimation){
                try {
                    count++;
                    count = count%4;
                    postInvalidate();
                    Thread.sleep(timeInterval);
                    switch (count){
                        case 1:
                            rectF1.set(0,maxLength-initLength1-moveLength1/4,rectfWidth,maxLength);
//                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF3.set(rectfWidth*2+horizonSpacing*2,maxLength-initLength3+moveLength3/2,rectfWidth*3+horizonSpacing*2,maxLength);
//                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 2:
                            rectF1.set(0,maxLength-initLength1-moveLength1/4*2,rectfWidth,maxLength);
//                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF3.set(rectfWidth*2+horizonSpacing*2,maxLength-initLength3+moveLength3,rectfWidth*3+horizonSpacing*2,maxLength);
//                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4*2,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 3:
                            rectF1.set(0,maxLength-initLength1-moveLength1/4,rectfWidth,maxLength);
//                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF3.set(rectfWidth*2+horizonSpacing*2,maxLength-initLength3+moveLength3/2,rectfWidth*3+horizonSpacing*2,maxLength);
//                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 0:
                            rectF1.set(0,maxLength-initLength1,rectfWidth,maxLength);
//                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF3.set(rectfWidth*2+horizonSpacing*2,maxLength-initLength3,rectfWidth*3+horizonSpacing*2,maxLength);
//                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    };


    private Thread playingThreadSlow = new Thread(){
        @Override
        public void run() {
            while (isAnimation){
                try {
                    count1++;
                    count1 = count1%4;
                    postInvalidate();
                    Thread.sleep(timeInterval+30);
                    switch (count1){
                        case 1:
//                            rectF1.set(0,maxLength-initLength1-moveLength1/4,rectfWidth,maxLength);
                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 2:
//                            rectF1.set(0,maxLength-initLength1-moveLength1/4*2,rectfWidth,maxLength);
                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4*2,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 3:
//                            rectF1.set(0,maxLength-initLength1-moveLength1/4,rectfWidth,maxLength);
                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4-moveLength4/4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                        case 0:
//                            rectF1.set(0,maxLength-initLength1,rectfWidth,maxLength);
                            rectF2.set(rectfWidth+horizonSpacing,maxLength-initLength2+moveLength2/2,rectfWidth*2+horizonSpacing,maxLength);
                            rectF4.set(rectfWidth*3+horizonSpacing*3,maxLength-initLength4,rectfWidth*4+horizonSpacing*3,maxLength);
                            break;
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    };



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if (widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = rectfWidth*4+horizonSpacing*3+getPaddingLeft()+getPaddingRight();
        }

        if (heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = maxLength+getPaddingBottom()+getPaddingTop();
        }

        setMeasuredDimension(width,height);
    }



    /**-==========对外提供的方法=============-*/
    public void pauseAnimation(){
        isAnimation =false;
    }
    public void startAnimation(){
        isAnimation = true;
        if (!playingThread.isAlive()){
            playingThread.start();
        }
        if (!playingThreadSlow.isAlive()){
            playingThreadSlow.start();
        }
    }
    public void setIndictorColor(int color){
        this.defaultColor = color;
    }

    public void setTimeInterval(long timeInterval1){
        this.timeInterval = timeInterval1;
    }
}
