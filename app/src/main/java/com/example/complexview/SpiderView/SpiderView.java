package com.example.complexview.SpiderView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SpiderView extends View {
    // 描边画笔，属性画笔
    private Paint radarPaint, valuePaint;

    private float radius; //网格最大半径
    private int centerX; //中心X
    private int centerY; //中心Y



    public SpiderView(Context context) {
        super(context);
        init();
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setColor(Color.GREEN);
        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL);
    }
}
