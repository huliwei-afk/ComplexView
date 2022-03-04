package com.example.complexview.seekbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.complexview.R;

import java.math.BigDecimal;


public class SkipDialogSeekbar extends View {

    /**
     * 三支画笔，用于画thumb，bg，以及progress
     */
    private Paint paintForThumb;
    private int thumbColor, thumbRadius;

    private Paint paintForBackground;
    private int backgroundColor, backgroundRadius;
    private float backgroundHeight;

    private Paint paintForProgress;
    private int progressColor, progressRadius;
    private float progressHeight;

    /**
     * bg和progress的圆角矩形
     * thumb的移动范围
     */
    private RectF backgroundRect, progressRect, thumbRect, recommendRect;

    /**
     * view总宽高
     */
    private float viewWidth, viewHeight;

    /**
     * 初始化时的进度
     */
    private float initialProgress;

    /**
     * thumb位置
     */
    private float thumbPositionX;

    /**
     * 最大最小进度
     */
    private float maxProgress = DEFAULT_MAX_PROGRESS, minProgress = DEFAULT_MIN_PROGRESS;

    /**
     * 默认最大最小进度
     */
    private static final float DEFAULT_MAX_PROGRESS = 60f, DEFAULT_MIN_PROGRESS = 0f;

    /**
     * 接口以及回调
     */
    private OnSkipDialogSeekbarChangeListener onSkipDialogSeekbarChangeListener;

    public SkipDialogSeekbar(Context context) {
        this(context, null);
    }

    public SkipDialogSeekbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkipDialogSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public interface OnSkipDialogSeekbarChangeListener {
        void onProgressChanged(float progress);
    }

    public void setOnSkipDialogSeekbarChangeListener(OnSkipDialogSeekbarChangeListener onSkipDialogSeekbarChangeListener) {
        this.onSkipDialogSeekbarChangeListener = onSkipDialogSeekbarChangeListener;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkipDialogSeekbar);

        thumbColor = a.getColor(R.styleable.SkipDialogSeekbar_sds_thumb_color, Color.GREEN);
        thumbRadius = a.getDimensionPixelOffset(R.styleable.SkipDialogSeekbar_sds_thumb_radius, dp2pxInt(12));

        backgroundColor = a.getColor(R.styleable.SkipDialogSeekbar_sds_background_color, Color.BLUE);
        backgroundRadius = a.getDimensionPixelOffset(R.styleable.SkipDialogSeekbar_sds_background_radius, dp2pxInt(23));
        backgroundHeight = a.getDimensionPixelOffset(R.styleable.SkipDialogSeekbar_sds_background_height, dp2pxInt(24));

        progressColor = a.getColor(R.styleable.SkipDialogSeekbar_sds_progress_color, Color.YELLOW);
        progressRadius = a.getDimensionPixelOffset(R.styleable.SkipDialogSeekbar_sds_progress_radius, dp2pxInt(23));
        progressHeight = a.getDimensionPixelOffset(R.styleable.SkipDialogSeekbar_sds_progress_height, dp2pxInt(24));

        initialProgress = a.getDimension(R.styleable.SkipDialogSeekbar_sds_initial_progress, 0);

        initPaintAndRectF();

        a.recycle();
    }

    private void initPaintAndRectF() {
        paintForThumb = new Paint();
        paintForThumb.setAntiAlias(true);
        paintForThumb.setStyle(Paint.Style.FILL);
        paintForThumb.setColor(thumbColor);

        paintForBackground = new Paint();
        paintForBackground.setAntiAlias(true);
        paintForBackground.setStyle(Paint.Style.FILL);
        paintForBackground.setColor(backgroundColor);

        paintForProgress = new Paint();
        paintForProgress.setAntiAlias(true);
        paintForProgress.setStyle(Paint.Style.FILL);
        paintForProgress.setColor(progressColor);

        backgroundRect = new RectF();
        progressRect = new RectF();
        thumbRect = new RectF();
        recommendRect = new RectF();
    }

    private void calculatePos(float x) {
        float endPos = getPaddingLeft() + viewWidth;
        float startPos = getPaddingLeft();
        float currentProgress = initialProgress;

        if (x >= startPos) {
            float result = (x - startPos) / viewWidth * (maxProgress - minProgress);
            BigDecimal bigDecimal = new BigDecimal(result).setScale(0, BigDecimal.ROUND_HALF_UP);

            currentProgress = bigDecimal.intValue();
            if (currentProgress > maxProgress) {
                currentProgress = maxProgress;
            }
        } else if (x < startPos) {
            currentProgress = minProgress;
        }
        if (currentProgress != initialProgress) {
            //发生变化才通知view重新绘制
            setSelectProgress(currentProgress);
        }
    }

    public void setSelectProgress(float initialProgress) {
        this.initialProgress = initialProgress;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                calculatePos(x);
                return true;

            case MotionEvent.ACTION_MOVE:
                calculatePos(x);
                return true;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        initValues(width, height);

        canvas.drawRoundRect(backgroundRect, backgroundRadius, backgroundRadius, paintForBackground);

        canvas.drawRoundRect(progressRect, progressRadius, progressRadius, paintForProgress);

        canvas.drawCircle(thumbPositionX, viewHeight / 2, thumbRadius, paintForThumb);

        canvas.drawRoundRect(recommendRect, 3,3, paintForThumb);

    }

    private void initValues(int width, int height){
        viewWidth = width - getPaddingLeft() - getPaddingRight();
        viewHeight = height;

        thumbPositionX = initialProgress / (maxProgress - minProgress) * viewWidth + getPaddingLeft();

        backgroundRect.set(getPaddingLeft() - thumbRadius / 2, (viewHeight - backgroundHeight) / 2, viewWidth + getPaddingLeft() + thumbRadius / 2, backgroundHeight / 2 + viewHeight / 2);
        progressRect.set(getPaddingLeft() - thumbRadius, (viewHeight - progressHeight) / 2, thumbPositionX + thumbRadius, viewHeight / 2 + progressHeight / 2);
        thumbRect.set(thumbPositionX - thumbRadius, viewHeight / 2 - thumbRadius / 2, thumbPositionX + thumbRadius, viewHeight / 2 + thumbRadius / 2);

        recommendRect.set(getPaddingLeft() + 100, 18, getPaddingLeft() + 106, 54);
    }

    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    public float getInitialProgress() {
        return initialProgress;
    }

    public void setInitialProgress(float initialProgress) {
        this.initialProgress = initialProgress;
    }
}
