package com.sunrin.necvcproject.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat


class BootReceiver : BroadcastReceiver() {
    private val powerOnAction = listOf(
        "android.intent.action.BOOT_COMPLETED", "android.intent.action.QUICKBOOT_POWERON"
    )

    override fun onReceive(context: Context, intent: Intent) {
        if (powerOnAction.contains(intent.action)) {
            ContextCompat.startForegroundService(
                context, Intent(context, AppForegroundService::class.java)
            )
        }
    }
}