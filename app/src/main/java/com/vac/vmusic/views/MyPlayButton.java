package com.vac.vmusic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by vac on 16/11/5.
 *
 */
public class MyPlayButton extends View{
    private Path path;
    private Paint paint;
    private int color = Color.argb(255,48,63,159);
    private int bigRadius = 30;
    private int smallRadius = 29;
    private int length = 20;
    private int strokWidth = 2;

    private Paint circlePaint;
    public MyPlayButton(Context context){
        super(context);
        init();
    }
    public MyPlayButton(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }

    public MyPlayButton(Context context,AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
        init();
    }
    private void init(){
        paint = new Paint();
        path = new Path();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        circlePaint = new Paint();
        circlePaint.setColor(color);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(strokWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(bigRadius+1,bigRadius+1,bigRadius,circlePaint);
        canvas.drawCircle(bigRadius+1,bigRadius+1,smallRadius,circlePaint);
        canvas.drawCircle(bigRadius+1,bigRadius+1,bigRadius-smallRadius,circlePaint);

        path.moveTo(bigRadius-(int)(length/2/1.732),bigRadius-length/2);
        path.lineTo((bigRadius+(int)(length/1.732)), bigRadius);
        path.lineTo(bigRadius-(int)(length/2/1.732), bigRadius+length/2);
        path.close();
        canvas.drawPath(path, paint);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(this.color);
        circlePaint.setColor(this.color);
        invalidate();
    }
    public void setLength(int length){
        this.length = length;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width,height;
        if (widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = getPaddingLeft()+getPaddingRight()+2*bigRadius+strokWidth*3;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = getPaddingBottom()+getPaddingTop()+2*bigRadius+strokWidth*3;
        }

        setMeasuredDimension(width,height);
    }

}
