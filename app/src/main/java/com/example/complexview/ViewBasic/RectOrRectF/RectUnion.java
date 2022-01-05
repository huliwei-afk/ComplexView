package com.example.complexview.ViewBasic.RectOrRectF;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RectUnion extends View {

    private Paint paint;
    private Rect rect_1;
    private Rect rect_2;
    private Rect rect_3;
    private Rect rect_4;


    public RectUnion(Context context) {
        super(context);
        init();
    }

    public RectUnion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RectUnion(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        rect_1 = new Rect(10,10,20,20);
        rect_2 = new Rect(100,100,110,110);

        rect_3 = new Rect(30, 30, 40, 40);
        rect_4 = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // test union
        // 合并矩形
        paint.setColor(Color.RED);
        canvas.drawRect(rect_1,paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(rect_2,paint);

        paint.setColor(Color.BLUE);
        rect_1.union(rect_2);
        canvas.drawRect(rect_1,paint);

        //合并点和矩形
        paint.setColor(Color.DKGRAY);
        rect_3.union(100,100);
        canvas.drawRect(rect_3, paint);

        paint.setColor(Color.YELLOW);
        rect_4.union(200, 200);
        canvas.drawRect(rect_4, paint);
    }
}
