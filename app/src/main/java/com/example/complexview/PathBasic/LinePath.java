package com.example.complexview.PathBasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinePath extends View {
    private Paint paint;
    private Path path;
    public LinePath(Context context) {
        super(context);
        init();
    }

    public LinePath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinePath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(10, 10);
        path.lineTo(10, 100);
        path.lineTo(300,100);
        path.close();

        canvas.drawPath(path, paint);
    }
}
