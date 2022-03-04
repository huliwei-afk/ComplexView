package com.example.complexview.ViewPosBasic;

import android.view.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.complexview.R;

public class NewFuncView extends View {

    private Paint paintForBackground;
    private Paint paintForText;

    private RectF backgroundRect;

    /**
     * 基线到字体上边框，下边框的距离
     */
    private float top, bottom;

    private int baseLineY;

    public NewFuncView(Context context) {
        this(context, null);
    }

    public NewFuncView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewFuncView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paintForBackground = new Paint();
        paintForBackground.setColor(getResources().getColor(R.color.colorPrimary));
        paintForBackground.setStyle(Paint.Style.FILL);
        paintForBackground.setAntiAlias(false);

        backgroundRect = new RectF(0, 0, 96, 42);

        paintForText = new Paint();
        paintForText.setColor(getResources().getColor(R.color.orange_f6));
        paintForText.setStyle(Paint.Style.FILL);
        paintForText.setAntiAlias(true);
        paintForText.setTextAlign(Paint.Align.CENTER);
        paintForText.setTextSize(24);

        // https://www.jb51.net/article/131779.htm
        // 坐标计算不是简单的取得rect的左边和右边就行，具体看文档
        Paint.FontMetrics fontMetrics = paintForText.getFontMetrics();
        top = fontMetrics.top;
        bottom = fontMetrics.bottom;
        baseLineY = (int) (backgroundRect.centerY() - top / 2 - bottom / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(backgroundRect, 6, 6, paintForBackground);
        canvas.drawText("新功能", backgroundRect.centerX(), baseLineY, paintForText);
    }
}
