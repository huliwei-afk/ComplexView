package com.example.complexview.PathBasic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import java.util.*

object PowerManagerCompat {

    private const val TAG = "PowerManagerCompat"

    interface PowerSaveModeChangeListener {
        /**
         * will be called when power save mode change, new state can be query via [PowerManagerCompat.isPowerSaveMode]
         */
        fun onPowerSaveModeChanged()
    }

    private val POWER_SAVE_MODE_VALUES = mapOf(
        "HUAWEI" to 4,
        "XIAOMI" to 1
    )

    private val POWER_SAVE_MODE_SETTING_NAMES = arrayOf(
        "SmartModeStatus", // huawei setting name
        "POWER_SAVE_MODE_OPEN" // xiaomi setting name
    )

    private val POWER_SAVE_MODE_CHANGE_ACTIONS = arrayOf(
        "huawei.intent.action.POWER_MODE_CHANGED_ACTION",
        "miui.intent.action.POWER_SAVE_MODE_CHANGED"
    )

    private const val monitorViaBroadcast = true

    /**
     * Monitor power save mode change, only support following devices
     * * Xiaomi
     * * Huawei
     */
    fun monitorPowerSaveModeChange(context: Context, powerSaveModeChangeListener: PowerSaveModeChangeListener) {
        if (Build.MANUFACTURER.toUpperCase(Locale.getDefault()) !in POWER_SAVE_MODE_VALUES.keys) {
            Log.w(TAG, "monitorPowerSaveModeChange: doesn't know how to monitor power save mode change for ${Build.MANUFACTURER}")
        }
        if (monitorViaBroadcast) {
            context.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    powerSaveModeChangeListener.onPowerSaveModeChanged()
                }
            }, IntentFilter().also {
                for (a in POWER_SAVE_MODE_CHANGE_ACTIONS) {
                    it.addAction(a)
                }
            })
        } else {
            val contentObserver = object : ContentObserver(null) {
                override fun onChange(selfChange: Boolean) {
                    super.onChange(selfChange)
                    powerSaveModeChangeListener.onPowerSaveModeChanged()
                }
            }
            for (name in POWER_SAVE_MODE_SETTING_NAMES) {
                context.contentResolver.registerContentObserver(
                    Uri.parse("content://settings/system/${name}"), false, contentObserver)
            }
        }
    }

    /**
     * Check the system is currently in power save mode
     * @see [PowerManager.isPowerSaveMode]
     */
    fun isPowerSaveMode(context: Context): Boolean {
        if (Build.MANUFACTURER.toUpperCase(Locale.getDefault()) in POWER_SAVE_MODE_VALUES.keys) {
            return isPowerSaveModeCompat(context)
        }
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        return powerManager?.isPowerSaveMode ?: false
    }

    private fun isPowerSaveModeCompat(context: Context): Boolean {
        for (name in POWER_SAVE_MODE_SETTING_NAMES) {
            val mode = Settings.System.getInt(context.contentResolver, name, -1)
            if (mode != -1) {
                return POWER_SAVE_MODE_VALUES[Build.MANUFACTURER.toUpperCase(Locale.getDefault())] == mode
            }
        }
        return false
    }
}