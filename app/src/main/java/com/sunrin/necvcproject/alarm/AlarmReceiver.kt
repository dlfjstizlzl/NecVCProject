package com.sunrin.necvcproject.alarm

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingIntent = AlarmUtils.getExistPendingIntent(context)
        pendingIntent?.cancel()

        val keyguardManager = context.getSystemService(KeyguardManager::class.java)
        Log.d("MyTag", "locked ${keyguardManager.isKeyguardLocked}")

        if (!keyguardManager.isKeyguardLocked) {
            context.sendBroadcast(Intent("START_OVERLAY"))
        }
    }
}