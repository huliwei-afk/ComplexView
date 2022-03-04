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

public class addPathView extends View {
    public addPathView(Context context) {
        super(context);
    }

    public addPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public addPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);
        //第二条路径顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);
        //依据路径布局文字
        String text="苦心人天不负,有志者事竟成";
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint1.setColor(Color.RED);
        paint.setTextSize(35);

        //先画出这两条路径
        canvas.drawPath(CCWRectpath, paint1);
        canvas.drawPath(CWRectpath, paint1);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针方向生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针方向生成
    }
}
