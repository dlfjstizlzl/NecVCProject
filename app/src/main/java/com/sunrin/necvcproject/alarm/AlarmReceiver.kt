package com.sunrin.necvcproject.alarm

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val keyguardManager = context.getSystemService(KeyguardManager::class.java)
        Log.d("MyTag", "locked ${keyguardManager.isKeyguardLocked}")

        if (!keyguardManager.isKeyguardLocked) {
            Toast.makeText(context, "사용시간 초과!", Toast.LENGTH_LONG).show()
        }
    }
}