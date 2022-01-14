package com.example.complexview.PathBasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ArcPath extends View {
    private Path path;
    private Paint paint;
    private RectF rectF;
    public ArcPath(Context context) {
        super(context);
        init();
    }

    public ArcPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        rectF = new RectF(100,10,200,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(10,10);
        path.arcTo(rectF,0,90);
        canvas.drawPath(path,paint);
    }
}
