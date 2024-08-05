package com.sunrin.necvcproject.alarm

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.sunrin.necvcproject.R

class AppForegroundService : Service() {
    private val receiver = ScreenReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTag", "AppForegroundService onCreate")
        registerReceiver(
            receiver, IntentFilter().apply {
                addAction(Intent.ACTION_USER_PRESENT)
                addAction(Intent.ACTION_SCREEN_OFF)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(this, "fg-service")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("서비스가 동작 중입니다.")
            .build()
        notificationManager.notify(1, notification)

        ServiceCompat.startForeground(
            this, 1, notification, 0
        )

        return super.onStartCommand(intent, flags, startId)
    }
}