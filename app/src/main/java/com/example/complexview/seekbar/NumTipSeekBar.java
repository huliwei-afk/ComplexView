package com.example.complexview.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.complexview.R;

import java.math.BigDecimal;

public class NumTipSeekBar extends View {
    private static final String TAG = NumTipSeekBar.class.getSimpleName();
    private RectF mTickBarRecf;

    /**
     * 默认的刻度条paint
     */
    private Paint mTickBarPaint;
    /**
     * 圆形按钮paint
     */
    private Paint mCircleButtonPaint;
    /**
     * 进度条paint
     */
    private Paint mProgressPaint;

    /**
     * 默认刻度条的高度
     */
    private float mTickBarHeight;
    /**
     * 默认刻度条的颜色
     */
    private int mTickBarColor;

    /**
     * 圆形按钮颜色
     */
    private int mCircleButtonColor;

    /**
     * 圆形按钮的半径
     */
    private float mCircleButtonRadius;
    /**
     * 圆形按钮的recf，矩形区域
     */
    private RectF mCircleRecf;


    /**
     * 进度条高度大小
     */
    private float mProgressHeight;

    /**
     * 进度颜色
     */
    private int mProgressColor;

    /**
     * 进度条的recf，矩形区域
     */
    private RectF mProgressRecf;
    /**
     * 选中的进度值
     */
    private int mSelectProgress;
    /**
     * 进度最大值
     */
    private int mMaxProgress = DEFAULT_MAX_VALUE;
    /**
     * 默认的最大值
     */
    private static final int DEFAULT_MAX_VALUE = 10;


    /**
     * view的总进度宽度，除去paddingtop以及bottom
     */
    private int mViewWidth;
    /**
     * view的总进度高度，除去paddingtop以及bottom
     */
    private int mViewHeight;
    /**
     * 圆形button的圆心坐标，也是progress进度条的最右边的的坐标
     */
    private float mCirclePotionX;

    /**
     * 监听进度条变化
     */
    public interface OnProgressChangeListener {
        void onChange(int selectProgress);
    }

    /**
     * 监听进度条变化
     */
    private OnProgressChangeListener mOnProgressChangeListener;

    /**
     * 设置进度条变化的监听器
     *
     * @param onProgressChangeListener
     */
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    public NumTipSeekBar(Context context) {
        this(context, null);
    }

    public NumTipSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumTipSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化view的属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.NumTipSeekBar);
        mTickBarHeight = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_tickBarHeight, getDpValue(8));
        mTickBarColor = attr.getColor(R.styleable.NumTipSeekBar_tickBarColor, getResources()
                .getColor(R.color.orange_f6));
        mCircleButtonColor = attr.getColor(R.styleable.NumTipSeekBar_circleButtonColor,
                getResources().getColor(R.color.white));
        mCircleButtonRadius = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_circleButtonRadius, getDpValue(16));
        mProgressHeight = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_progressHeight, getDpValue(20));
        mProgressColor = attr.getColor(R.styleable.NumTipSeekBar_progressColor,
                getResources().getColor(R.color.white));
        mSelectProgress = attr.getInt(R.styleable.NumTipSeekBar_selectProgress, 0);
        mMaxProgress = attr.getInt(R.styleable.NumTipSeekBar_maxProgress, 10);

        initView();

        attr.recycle();


    }

    private void initView() {
        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setAntiAlias(true);

        mCircleButtonPaint = new Paint();
        mCircleButtonPaint.setColor(mCircleButtonColor);
        mCircleButtonPaint.setStyle(Paint.Style.FILL);
        mCircleButtonPaint.setAntiAlias(true);

        mTickBarPaint = new Paint();
        mTickBarPaint.setColor(mTickBarColor);
        mTickBarPaint.setStyle(Paint.Style.FILL);
        mTickBarPaint.setAntiAlias(true);

        mTickBarRecf = new RectF();
        mProgressRecf = new RectF();
        mCircleRecf = new RectF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        Log.i(TAG, "onTouchEvent: x：" + x);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_DOWN:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void judgePosition(float x) {
        float end = getPaddingLeft() + mViewWidth;
        float start = getPaddingLeft();
        int progress = mSelectProgress;
        Log.i(TAG, "judgePosition: x-start：" + (x - start));
        Log.i(TAG, "judgePosition: start:" + start + "  end:" + end + "  mMaxProgress:" +
                mMaxProgress);
        if (x >= start) {
            double result = (x - start) / mViewWidth * (float) mMaxProgress;
            BigDecimal bigDecimal = new BigDecimal(result).setScale(0, BigDecimal.ROUND_HALF_UP);

            progress = bigDecimal.intValue();
            if (progress > mMaxProgress) {
                progress = mMaxProgress;
            }
        } else if (x < start) {
            progress = 0;
        }
        if (progress != mSelectProgress) {
            //发生变化才通知view重新绘制
            setSelectProgress(progress);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        initValues(width, height);
        if (mOnProgressChangeListener != null) {
            mOnProgressChangeListener.onChange(mSelectProgress);
        }

        canvas.drawRoundRect(mTickBarRecf, mProgressHeight / 2, mProgressHeight / 2,
                mTickBarPaint);
        canvas.drawRoundRect(mProgressRecf, mProgressHeight / 2, mProgressHeight / 2,
                mProgressPaint);
        canvas.drawCircle(mCirclePotionX, mViewHeight / 2, mCircleButtonRadius,
                mCircleButtonPaint);
    }

    private void initValues(int width, int height) {
        mViewWidth = width - getPaddingRight() - getPaddingLeft();
        mViewHeight = height;
        mCirclePotionX = (float) mSelectProgress / mMaxProgress * mViewWidth + getPaddingLeft();

        if (mTickBarHeight > mViewHeight) {
            //如果刻度条的高度大于view本身的高度的1/2，则显示不完整，所以处理下。
            mTickBarHeight = mViewHeight;
        }
        mTickBarRecf.set(getPaddingLeft(), (mViewHeight - mTickBarHeight) / 2,
                mViewWidth + getPaddingLeft(), mTickBarHeight / 2 +
                        mViewHeight / 2);
        if (mProgressHeight > mViewHeight) {
            //如果刻度条的高度大于view本身的高度的1/2，则显示不完整，所以处理下。
            mProgressHeight = mViewHeight;
        }

        mProgressRecf.set(getPaddingLeft(), (mViewHeight - mProgressHeight) / 2,
                mCirclePotionX, mProgressHeight / 2 + mViewHeight / 2);

        if (mCircleButtonRadius > mViewHeight / 2) {
            //如果圆形按钮的半径大于view本身的高度的1/2，则显示不完整，所以处理下。
            mCircleButtonRadius = mViewHeight / 2;
        }
        mCircleRecf.set(mCirclePotionX - mCircleButtonRadius, mViewHeight / 2 -
                        mCircleButtonRadius / 2,
                mCirclePotionX + mCircleButtonRadius, mViewHeight / 2 +
                        mCircleButtonRadius / 2);
    }


    /**
     * 设置当前选中的值
     *
     * @param selectProgress
     */
    public void setSelectProgress(int selectProgress) {
        mSelectProgress = selectProgress;
        invalidate();
    }

    /**
     * 获取dp对应的px值
     *
     * @param w
     * @return
     */
    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext()
                .getResources().getDisplayMetrics());
    }
}