package com.alex.location.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder
import com.alex.location.R
import com.alex.location.helper.*
import java.util.*


/**
 * Control notification bar show and hide
 */
class NotificationService : Service() {
    companion object {
        const val TAG = "NotificationService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("RestrictedApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        stopForeground(true)
        var weatherBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_weather_normal)
        startForeground(Util.FOREGROUND_NOTIFICATION_ID,
            Util.createStatusBarNotification(this, getString(R.string.app_name), weatherBitmap))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

}