package com.sunrin.necvcproject.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.AlarmManagerCompat

class ScreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: "N/A"
        val alarmManager = context.getSystemService(AlarmManager::class.java)

        val prevAlarmIntent = PendingIntent.getBroadcast(
            context, 0,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        Log.d("MyTag", "prevAlarm ${action} ${prevAlarmIntent != null}")

        if (prevAlarmIntent != null) {
            alarmManager.cancel(prevAlarmIntent)
            prevAlarmIntent.cancel()
        }

        if (intent.action.equals(Intent.ACTION_USER_PRESENT)) {
            val alarmIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, AlarmReceiver::class.java).apply {

                },
                PendingIntent.FLAG_IMMUTABLE
            )

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (5 * 1000),
                alarmIntent!!
            )

            Log.d("MyTag", "alarm registered")
        }
    }
}