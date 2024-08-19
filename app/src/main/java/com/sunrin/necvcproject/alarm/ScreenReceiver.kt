package com.sunrin.necvcproject.alarm

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import kotlin.math.max

class ScreenReceiver : BroadcastReceiver() {
    companion object {
        var lastScreenOn: Long? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val pref = context.getSharedPreferences("alarm", Context.MODE_PRIVATE)
        val enabled = pref.getBoolean("enable", false)
        val overlay = pref.getBoolean("overlay", false)
        if (!enabled || overlay) {
            return
        }

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        AlarmUtils.cancelExistAlarm(alarmManager, context)

        val usableTime = pref.getLong("usableTime", 0)
        when {
            intent.action.equals(Intent.ACTION_USER_PRESENT) || intent.action.equals("ALARM_RESTART") -> {
                Log.d("MyTag", "alarm register $usableTime ${intent.action}")

                val alarmIntent = AlarmUtils.createPendingIntent(context)
                AlarmUtils.createAlarm(alarmManager, alarmIntent, usableTime)
                lastScreenOn = SystemClock.elapsedRealtime()
                pref.edit().putLong("lastScreenOn", lastScreenOn!!).apply()
            }

            intent.action.equals(Intent.ACTION_SCREEN_OFF) -> {
                if (lastScreenOn != null) {
                    val usedTime = SystemClock.elapsedRealtime() - lastScreenOn!!
                    val remainTime = max(usableTime - usedTime, 0L)

                    Log.d("MyTag", "usedTime: $usedTime remainTime: $remainTime")

                    pref.edit().putLong("usableTime", remainTime).apply()

                    lastScreenOn = null
                }
            }
        }
    }
}