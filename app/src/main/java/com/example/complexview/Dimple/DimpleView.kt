package com.example.complexview.Dimple

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DimpleView @JvmOverloads constructor(context: Context,
                                           attributeSet: AttributeSet? = null,
                                           defStyleId: Int = 0)
    : View(context, attributeSet, defStyleId){

    private var pointList = mutableListOf<Point>()
    private var paint : Paint = Paint()
    private var centerX : Float = 0F
    private var centerY : Float = 0F
    private val random = Random()

    //定义动画
    private var animator = ValueAnimator.ofFloat(0f, 1f)
    init {
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updatePoint(it.animatedValue as Float)
            invalidate()//重绘界面
        }
    }

    //定义一个Path，让粒子都跟着Path走
    private var path = Path()
    private var pathMeasure = PathMeasure()
    private var pos = FloatArray(2) //扩散圆上某一点的x,y
    private val tan = FloatArray(2)//扩散圆上某一点切线

    //粒子不断往下掉的话，那应该是y值不断地增加就可以了
    //将粒子沿着不同的各自方向衍射出去，用到cos和sin函数
    private fun updatePoint(value: Float) {
        pointList.forEach { point ->
            if (point.offset > point.maxOffset) {
                point.offset = 0
                point.speed = (random.nextInt(10) + 5).toFloat()
            }
            point.alpha = ((1f - point.offset / point.maxOffset) * 225f).toInt()
            point.x = (centerX + cos(point.angle) * (280f + point.offset)).toFloat()
            if (point.y > centerY) {
                point.y = (sin(point.angle) * (280f + point.offset) + centerY).toFloat()
            } else {
                point.y = (centerY - sin(point.angle) * (280f + point.offset)).toFloat()
            }
            point.offset += point.speed.toInt()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        //draw出每一个点
        pointList.forEach {
            paint.alpha = it.alpha
            canvas.drawCircle(it.x, it.y, it.radius, paint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        val random = Random()
        var nextX = 0F
        var nextY = 0F
        //定义一个速度
        var speed = 0

        var angle = 0.0

        path.addCircle(centerX, centerY, 280f, Path.Direction.CCW)//添加一个圆
        pathMeasure.setPath(path, false)

        //尽量不要在onDraw方法里频繁创建对象，对性能不好
        for (i in 0..500) {
            //按比例测量路径上每一点的值
            pathMeasure.getPosTan(i / 500f * pathMeasure.length, pos, tan)
            nextX = pos[0]+random.nextInt(6) - 3f //X值随机偏移
            nextY=  pos[1]+random.nextInt(6) - 3f//Y值随机偏移
            angle = acos(((pos[0] - centerX) / 280f).toDouble())
            speed= random.nextInt(10)+5
            pointList.add(
                Point(nextX, nextY, 2f, speed.toFloat(), 100,300f,0, angle)
            )
        }
        //开启动画
        animator.start()
    }
}