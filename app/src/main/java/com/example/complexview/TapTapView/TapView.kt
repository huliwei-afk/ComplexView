package com.example.complexview.TapTapView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class TapView @JvmOverloads
constructor(context : Context, attributeSet : AttributeSet? = null, defStyleId : Int = 0) :
    AppCompatImageView(context, attributeSet, defStyleId){

    //圆角矩形的Path
    private val path = Path()

    private val paint = Paint().apply {
        //开启抗锯齿
        isAntiAlias = true
        color = Color.RED
    }

    private val offset = 5f
    private val duration = 500
    //初始化动画
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "rotation", offset, 0f, -offset, 0f, offset, 0f, -offset, 0f, offset, 0f)
    }.apply {
        this.value.duration = duration.toLong()
    }

    override fun onDraw(canvas: Canvas) {
        //开启离屏缓冲
        val count = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint)
        //path添加一个圆角矩形
        path.addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 30f, 30f, Path.Direction.CW)
        //Canvas裁切成一个圆角矩形
        canvas.clipPath(path)
        //调用AppCompatImageView的onDraw方法
        super.onDraw(canvas)
        //恢复离屏缓冲
        canvas.restoreToCount(count)
    }

    //实现双击拍一拍
    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){
        //接管点击事件
        override fun onDown(e: MotionEvent?): Boolean = true

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            shake()
            return true
        }
    })

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    //确定轴心为宽的一半，高的底部
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        pivotX = width / 2f
        pivotY = height.toFloat()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    //开启动画
    fun shake(){
        animator.start()
    }

}