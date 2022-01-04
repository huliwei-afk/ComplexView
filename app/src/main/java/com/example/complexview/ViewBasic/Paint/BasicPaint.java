package com.example.complexview.ViewBasic.Paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BasicPaint extends View {
    public BasicPaint(Context context) {
        super(context);
    }

    public BasicPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 设置画笔基本属性
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);

        canvas.drawCircle(190, 200, 150, paint);
    }
}
