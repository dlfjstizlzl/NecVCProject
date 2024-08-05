package com.sunrin.necvcproject.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat

class AlarmUtils {
    companion object {
        fun getExistPendingIntent(context: Context): PendingIntent? {
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
            )
        }

        fun createPendingIntent(context: Context): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        fun cancelExistAlarm(alarmManager: AlarmManager, context: Context) {
            val exist = getExistPendingIntent(context)
            if (exist != null) {
                alarmManager.cancel(exist)
                exist.cancel()
            }
        }

        fun createAlarm(alarmManager: AlarmManager, alarmIntent: PendingIntent, after: Long) {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + after,
                alarmIntent
            )
        }
    }
}