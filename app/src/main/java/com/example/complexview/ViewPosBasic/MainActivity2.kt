package com.example.complexview.ViewPosBasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.complexview.R

class MainActivity2 : AppCompatActivity() {

    val TAG = "MAINACTIVITY2"

    //在onCreate或onResume中想要拿到宽高是不现实的，因为View还没有绘制完成
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var textView = findViewById<TextView>(R.id.location)

        textView.viewTreeObserver.addOnGlobalLayoutListener {
            //获取自身宽高
            var height = textView.height
            var width = textView.width
            Log.d(TAG, "View Height = $height, View Width = $width") //wrapConent时 都为0

            //获取到父控件的距离
            var top = textView.top
            var left = textView.left
            var right = textView.right
            var bottom = textView.bottom
            Log.d(TAG, "View Top = $top, View Left = $left, View Right = $right, View Bottom = $bottom")

            //拿到padding
            var paddingTop = textView.paddingTop
            var paddingLeft = textView.paddingLeft
            var paddingRight = textView.paddingRight
            var paddingBottom = textView.paddingBottom
            Log.d(TAG, "View PTop = $paddingTop, View PLeft = $paddingLeft, View PRight = $paddingRight, View PBottom = $paddingBottom")
        }

    }


}