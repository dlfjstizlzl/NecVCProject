package com.sunrin.necvcproject.alarm

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.sunrin.necvcproject.R
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme


class AppForegroundService : LifecycleService(), SavedStateRegistryOwner {
    private val receiver = ScreenReceiver()
    private val localReceiver = LocalReceiver()

    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    private var showingOverlay = false
    private var view: View? = null
    private val windowManager by lazy {
        getSystemService(WindowManager::class.java)
    }

    private val inflate by lazy {
        getSystemService(LayoutInflater::class.java)
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTag", "AppForegroundService onCreate")

        ContextCompat.registerReceiver(
            this, receiver, IntentFilter().apply {
                addAction(Intent.ACTION_USER_PRESENT)
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction("ALARM_RESTART")
            }, ContextCompat.RECEIVER_EXPORTED
        )

        ContextCompat.registerReceiver(
            this, localReceiver, IntentFilter().apply {
                addAction("START_OVERLAY")
            }, ContextCompat.RECEIVER_NOT_EXPORTED
        )

        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)

        val pref = getSharedPreferences("alarm", Context.MODE_PRIVATE)
        if (pref.getBoolean("overlay", false)) {
            startOverlay()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        unregisterReceiver(localReceiver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(this, "fg-service")
            .setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("서비스가 동작 중입니다.")
            .build()
        notificationManager.notify(1, notification)

        ServiceCompat.startForeground(
            this, 1, notification, 0
        )

        return super.onStartCommand(intent, flags, startId)
    }

    fun startOverlay() {
        if (!showingOverlay) {
            val pref = getSharedPreferences("alarm", Context.MODE_PRIVATE)
            pref.edit().putBoolean("overlay", true).apply()

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
            )

            view = inflate.inflate(R.layout.layout_overlay, null)
            view!!.setViewTreeLifecycleOwner(this)
            view!!.setViewTreeSavedStateRegistryOwner(this)

            view!!.findViewById<ComposeView>(R.id.composeView).setContent {
                OverlayScreen(this)
            }

            windowManager.addView(view, params)
            view!!.requestFocus()

            showingOverlay = true
        }
    }

    @SuppressLint("ApplySharedPref")
    fun closeOverlay() {
        val pref = getSharedPreferences("alarm", Context.MODE_PRIVATE)
        val initTime = pref.getLong("initTime", 60 * 1000)
        pref.edit().putBoolean("overlay", false).putLong("usableTime", initTime).commit()

        if (view != null) {
            windowManager.removeView(view)
            view = null
        }

        sendBroadcast(Intent("ALARM_RESTART"))
        showingOverlay = false
    }

    inner class LocalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals("START_OVERLAY")) {
                startOverlay()
            }
        }
    }
}