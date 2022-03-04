package com.example.complexview.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.complexview.R;


public class TextSeekBar extends View {

    private static final String TAG = "TextSeekBar";

    private static final int MAX_LEVEL = 10000;
    private static final int DEF_TEXT_SIZE = 16;
    private static final int DEF_MAX_PROGRESS = 100;

    private int mWidth, mHeight;

    private int mProgress = 0;
    private int mMaxProgress;

    private boolean mIsDragging;
    //背景图
    private Drawable mBackgroundDrawable;
    private float mBackgroundHeight;
    private float mBackgroundWidth;
    private float mBackgroundLeft, mBackgroundTop, mBackgroundRight, mBackgroundBottom;
    //progress
    private ClipDrawable mProgressDrawable;

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    //thumb
    private Drawable mThumb;
    private int mThumbLeft, mThumbTop, mThumbBottom, mThumbRight;
    private int mThumbWith = 0, mThumbHeight = 0;
    //文字
    private Drawable mTextBgDrawable;
    private int mTextBgWidth = 0, mTextBgHeight = 0;

    public TextSeekBar(Context context) {
        super(context);
        init();
    }

    public TextSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextSeekBar);

            mThumb = typedArray.getDrawable(R.styleable.TextSeekBar_thumb);
            mThumbWith = (int) typedArray.getDimension(R.styleable.TextSeekBar_thumbWidth, mThumbWith);
            mThumbHeight = (int) typedArray.getDimension(R.styleable.TextSeekBar_thumbHeight, mThumbHeight);
            //progress
            Drawable progressLayer = typedArray.getDrawable(R.styleable.TextSeekBar_progressDrawable);
            if (progressLayer instanceof LayerDrawable) {
                //取背景图
                mBackgroundDrawable = ((LayerDrawable) progressLayer).findDrawableByLayerId(android.R.id.background);

                //取progress图
                Drawable progressDrawable = ((LayerDrawable) progressLayer).findDrawableByLayerId(android.R.id.progress);
                if (progressDrawable instanceof ClipDrawable) {
                    mProgressDrawable = (ClipDrawable) progressDrawable;
                    Log.d(TAG, "TextSeekBar: mProgressDrawable instanceof Clip");
                }
            }

            mTextBgDrawable = typedArray.getDrawable(R.styleable.TextSeekBar_textBackground);

            mTextBgWidth = (int) typedArray.getDimension(R.styleable.TextSeekBar_textBgWidth, mTextBgWidth);
            mTextBgHeight = (int) typedArray.getDimension(R.styleable.TextSeekBar_textBgHeight, mTextBgHeight);

            typedArray.recycle();
        }
        init();
    }

    private void init() {

        mMaxProgress = DEF_MAX_PROGRESS;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG, "onMeasure:  " + width + " h " + height);

        mBackgroundHeight = height;

        //重设布局宽高
        height = Math.max(height + mTextBgHeight, mThumbHeight + mTextBgHeight);
        width = Math.max(width + mThumbWith, width + mTextBgWidth);

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //计算背景宽度
        mBackgroundWidth = Math.min(mWidth - mThumbWith, mWidth - mTextBgWidth);
        //计算背景margin
        mBackgroundLeft = Math.max(mThumbWith / 2, mTextBgWidth / 2);
        mBackgroundRight = mBackgroundLeft + mBackgroundWidth;
        mBackgroundTop = (mThumbHeight - mBackgroundHeight) / 2 + mTextBgHeight;
        mBackgroundBottom = mBackgroundTop + mBackgroundHeight;
        //mThumbLeft, mThumbTop, mThumbBottom, mThumbRight
        mThumbLeft = Math.max(mTextBgWidth / 2 - mThumbWith / 2, 0);
        mThumbTop = mTextBgHeight;
        mThumbRight = mThumbLeft + mThumbWith;
        mThumbBottom = mThumbTop + mThumbHeight;

        if (mBackgroundDrawable != null) {
            mBackgroundDrawable.setBounds((int) mBackgroundLeft, (int) mBackgroundTop, (int) mBackgroundRight, (int) mBackgroundBottom);
        }
        if (mProgressDrawable != null) {
            mProgressDrawable.setBounds((int) mBackgroundLeft, (int) mBackgroundTop, (int) mBackgroundRight, (int) mBackgroundBottom);
        }

        Log.d(TAG, "onSizeChanged: " + mWidth + " height " + mHeight + " mBackHeight " + mBackgroundHeight + " mTop " + mBackgroundTop + " thumbWidth " + mThumbWith + " " + mBackgroundLeft + " " + mBackgroundBottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(0, 0, mWidth, mHeight, mThumbPaint);

        //绘制背景图
        drawBackground(canvas);
        //绘制progress
        drawProgress(canvas);
        //绘制thumb
        drawThumb(canvas);
    }


    private void drawBackground(Canvas canvas) {
        if (mBackgroundDrawable != null) {
            mBackgroundDrawable.draw(canvas);
        }
    }

    private void drawProgress(Canvas canvas) {
        if (mProgressDrawable != null) {
            mProgressDrawable.setLevel((int) (getFraction(mProgress) * MAX_LEVEL));
            mProgressDrawable.draw(canvas);
        }
    }

    private void drawThumb(Canvas canvas) {
        if (mThumb != null) {
            mThumb.setBounds((int) (getFraction(mProgress) * mBackgroundWidth) + mThumbLeft, mThumbTop, (int) (getFraction(mProgress) * mBackgroundWidth + mThumbRight), mThumbBottom);
            mThumb.draw(canvas);
        }
    }


    /**
     * 获取进度百分比
     *
     * @param progress
     * @return
     */
    private float getFraction(int progress) {
        return (float) (progress * 1.0 / mMaxProgress);
    }

    public void setProgress(int progress) {
        if (mProgress == progress) {
            return;
        }
        mProgress = progress;
        invalidate();
        onProgressRefresh();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setMax(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }


    private void onProgressRefresh() {
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onProgressChanged(this, mProgress, mIsDragging);
        }
    }

    private int getProgress(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float availableWidth = mBackgroundWidth;
        float fraction = (x - mBackgroundLeft) / availableWidth;
        fraction = Math.max(0, fraction);
        fraction = Math.min(fraction, 1);
        return (int) (fraction * getMax());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mIsDragging = true;
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStartTrackingTouch(this);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mIsDragging) {
                    setProgress(getProgress(event));
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mOnSeekBarChangeListener != null && mIsDragging) {
                    mOnSeekBarChangeListener.onStopTrackingTouch(this);
                }
                mIsDragging = false;
                break;
        }

        return super.onTouchEvent(event);
    }


    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
    }


    public interface OnSeekBarChangeListener {
        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(TextSeekBar seekBar, int progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStartTrackingTouch(TextSeekBar seekBar);

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStopTrackingTouch(TextSeekBar seekBar);
    }

}
