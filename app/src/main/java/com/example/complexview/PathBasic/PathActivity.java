package com.example.complexview.PathBasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.complexview.R;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        Button button = findViewById(R.id.button);
        // 系统dialog加入电池优化白名单
//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//                boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(PathActivity.this.getPackageName());
//                Log.d("BatteryOptimize", "hasIgnored = " + hasIgnored);
//                //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
//                if (!hasIgnored) {
//                    Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                    intent.setData(Uri.parse("package:" + getPackageName()));
//                    startActivity(intent);
//                }
//            }
//        });

        // 跳转手机电池界面
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent powerUsageIntent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
//                ResolveInfo resolveInfo = getPackageManager().resolveActivity(powerUsageIntent, 0);
//                // check that the Battery app exists on this device
//                if(resolveInfo != null){
//                    startActivity(powerUsageIntent);
//                }
//            }
//        });

        // 跳转应用管理
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent detailInfo = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                String pkg = "com.android.settings";
//                String cls = "com.android.settings.applications.InstalledAppDetails";
//                detailInfo.setComponent(new ComponentName(pkg, cls));
//                detailInfo.setData(Uri.parse("package:" + getPackageName()));
//                startActivity(detailInfo);
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_QUICK_LAUNCH_SETTINGS);
//
//                startActivity(intent);
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int wifiSleepValue = Settings.System.getInt(getContentResolver(),Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
//                boolean check = wifiSleepValue == Settings.System.WIFI_SLEEP_POLICY_NEVER;
//                Log.d("WIFI", "check = " + check);
//            }
//        });

        // 智能省流量
//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                int trafficValue = getSystemService(ConnectivityManager.class).getRestrictBackgroundStatus();
//                boolean disable = trafficValue == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED;
//                boolean enable = trafficValue == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED;
//                boolean whiteList = trafficValue == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_WHITELISTED;
//                Log.d("Traffic", "disable = " + disable + " enable = " + enable + " whiteList = " + whiteList);
//            }
//        });


//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                Log.d("Power", "isPowerSaveMode = " + PowerManagerCompat.INSTANCE.isPowerSaveMode(PathActivity.this));
//                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//                Log.d("Power", "isPowerSaveMode = " + powerManager.isPowerSaveMode());
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Util.startToAutoStartSetting(PathActivity.this);
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivity = (ConnectivityManager)PathActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
                if(networkInfo == null) {
                    Log.d("Background", "available = " + false);
                    return;
                }
                Log.d("Background", "available = " + networkInfo);
            }
        });
    }
}