package com.example.complexview.ViewBasic.RectOrRectF;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BasicRect extends View {

    private Rect rect;
    private Paint paint;
    private int eventX;
    private int eventY;

    public BasicRect(Context context) {
        super(context);
        init();
    }

    public BasicRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        rect = new Rect(100,10,300,100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX = (int) event.getX();
        eventY = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            eventX = -1;
            eventY = -1;
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect.contains(eventX, eventY)) {
            paint.setColor(Color.RED);
        } else {
             paint.setColor(Color.GREEN);
        }
        canvas.drawRect(rect, paint);
    }
}
