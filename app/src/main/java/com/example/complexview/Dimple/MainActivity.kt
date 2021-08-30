package com.example.complexview.Dimple

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.complexview.R
import com.example.complexview.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var rotateAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //圆形
        rotateAnimator = ObjectAnimator.ofFloat(mainBinding.musicAvatar, View.ROTATION, 0f, 360f)
        rotateAnimator.duration = 6000
        rotateAnimator.repeatCount = -1
        rotateAnimator.interpolator = LinearInterpolator()

        lifecycleScope.launch(Dispatchers.Main) {
            loadImage()
        }
        rotateAnimator.start()
    }
    private suspend fun loadImage() {
        withContext(Dispatchers.IO) {
            Glide.with(this@MainActivity)
                .load(R.drawable.ic_launcher_round)
                .circleCrop()
                .into(object : ImageViewTarget<Drawable>(mainBinding.musicAvatar) {
                    override fun setResource(resource: Drawable?) {
                        mainBinding.musicAvatar.setImageDrawable(resource)
                    }
                })
        }
    }
}