package com.vac.vmusic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by vac on 16/11/11.
 *
 */
@SuppressWarnings("NewApi")
public class MyBorderTextView extends TextView{
    private Paint mPaint = null;
    private RectF rectF;
    private float strokeWidth = 4f;
    private float cornerRadius = 4;
    public MyBorderTextView(Context context){
        super(context);
        init();
    }
    public MyBorderTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }
    public MyBorderTextView(Context context,AttributeSet attributeSet,int defType){
        super(context,attributeSet,defType);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FF4081"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(strokeWidth/2,strokeWidth/2,getMeasuredWidth()-strokeWidth,
                getMeasuredHeight()-strokeWidth,cornerRadius,cornerRadius,mPaint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width,height;
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = getPaddingBottom()+getPaddingTop()+(int)strokeWidth+getMeasuredHeight();
        }
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = getPaddingLeft()+getPaddingRight()+(int)strokeWidth+getMeasuredWidth();
        }
        setMeasuredDimension(width,height);
    }
}
