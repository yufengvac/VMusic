package com.vac.vmusic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vac on 16/11/5.
 *
 */
public class MyManagerButton extends View{

    private Paint paint,otherPaint;
    private Path path;
    private int color = Color.argb(255,48,63,159);
    private float strokeWidth = 1f;
    RectF rect ,smallRect;

    RectF rect2,smallRect2;
    RectF rect4,smallRect4;

    private int length = 20;

    private int margin = 5;

    private float other = 1.414f;
    public MyManagerButton(Context context){
        super(context);
        init();
    }
    public MyManagerButton(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }

    public MyManagerButton(Context context,AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
        init();
    }
    private void init(){
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        other = length/1.414f-length/2;

        otherPaint = new Paint();
        otherPaint.setColor(color);
        otherPaint.setAntiAlias(true);
        otherPaint.setStyle(Paint.Style.STROKE);
        otherPaint.setStrokeWidth(2f);

        rect =  new RectF(0, other, length, length+other);
        smallRect = new RectF(1,other+1,length-1,length+other-1);

        rect2 = new RectF(0,margin+length*1.414f,length,length+margin+length*1.414f);
        smallRect2 = new RectF(1,margin+length*1.414f+1,length-1,length+margin+length*1.414f-1);


        rect4 = new RectF(margin+length/2+length/1.414f,margin+length*1.414f,margin+length/2+length/1.414f+length,length+margin+length*1.414f);
        smallRect4 = new RectF(1+margin+length/2+length/1.414f,margin+length*1.414f+1,margin+length/2+length/1.414f+length-1,length+margin+length*1.414f-1);

        path = new Path();
    }

    public void setColor(int color_){
        this.color = color_;
        paint.setColor(color);
        otherPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, paint);
        canvas.drawRect(smallRect,paint);

        canvas.drawRect(rect2, paint);
        canvas.drawRect(smallRect2,paint);

        canvas.drawRect(rect4, paint);
        canvas.drawRect(smallRect4,paint);

        path.moveTo(length+margin+(int)(length/1.414f),0);
        path.lineTo(length+margin,(int)(length/1.414f));
        path.lineTo(length+margin+(int)(length/1.414f),(int)(length*1.414f));
        path.lineTo(length+margin+(int)(length*1.414f),(int)(length/1.414f));
        path.close();
        canvas.drawPath(path,otherPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width ,height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = getPaddingLeft()+getPaddingRight()+length+margin+(int)(1.414*length)+(int)(strokeWidth*4)+4;
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height =heightSize;
        }else {
            height = getPaddingBottom()+getPaddingTop()+length+margin+(int)(1.144*length)+(int)(strokeWidth*4)+6;
        }

        setMeasuredDimension(width,height);

    }
}
