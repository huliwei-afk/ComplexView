package com.example.complexview.seekbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.complexview.R;

public class SeekActivity extends AppCompatActivity {
    int cacheProgress = 0;
    int progress;
    int maxProgress = 60;
    private CustomSeekBar customSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ConstraintLayout insertLayout = findViewById(R.id.check_layout);
        TextView textView = new TextView(SeekActivity.this);
        textView.setText("建议位置");

        ConstraintLayout.LayoutParams s = new ConstraintLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        s.topMargin = 27;
        s.leftMargin = 72;
        s.leftToLeft = R.id.check_layout;
        s.topToBottom = R.id.seekbar;
        // 翻3倍

        textView.setLayoutParams(s);
        insertLayout.addView(textView);

        customSeekBar = findViewById(R.id.seek_bar);
        customSeekBar.setMaxProgress(maxProgress);//最大进度s
        customSeekBar.setProgressBarHeight(1.0f);//进度条高度dp 默认1.0f
        customSeekBar.setCacheProgressBarHeight(1.5f);//缓存条高度dp 默认1.5f
        customSeekBar.setProgressBarColor(android.R.color.black);//进度条颜色colorId
        customSeekBar.setCacheProgressBarColor(android.R.color.black);//缓存条颜色colorId
        customSeekBar.setTextBgColor(android.R.color.white);//文字背景颜色colorId
        customSeekBar.setTextColor(android.R.color.black);//字体颜色colorId
        customSeekBar.setTextSize(10);//文字大小sp 默认10sp
        // 设置进度拖动监听
        // 手动拖动进度条会返回当前进度
        customSeekBar.setProgressListener(new CustomSeekBar.IProgressListener() {
            @Override
            public void progress(int progress) {
                SeekActivity.this.progress = progress;
            }
        });
        start();
    }

    private void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (cacheProgress < maxProgress) {
                            cacheProgress += 4;
                            customSeekBar.cacheProgress(cacheProgress);
                        }
                        customSeekBar.progress(progress);
                        if (progress >= maxProgress) {
                            break;
                        }
                        progress += 1;
                        Log.e("ss", "ss" + progress);

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}