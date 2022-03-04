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
    private Path path, path2;
    private Paint paint, paint2;
    private RectF rectF, rectF2;
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
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        path2 = new Path();
        rectF = new RectF(100,10,200,100);
        rectF2 = new RectF(200, 110, 300 ,200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(10,10);
        path.arcTo(rectF,0,90);
        canvas.drawPath(path,paint);

        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.STROKE);
        path2.moveTo(110, 110);
        path2.arcTo(rectF2, 0,90, true);
        canvas.drawPath(path2, paint2);
    }
}
