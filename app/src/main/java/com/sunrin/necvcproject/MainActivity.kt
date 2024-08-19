package com.sunrin.necvcproject

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.sunrin.necvcproject.alarm.AppForegroundService
import com.sunrin.necvcproject.screen.StartScreen
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NecVCProjectTheme {
                Scaffold(topBar = {}) { innerpadding ->
                    Greeting(innerpadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(innerPadding: PaddingValues) {
    val context = LocalContext.current

    // 권한 요청 런처 설정
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            checkAndStartService(context)
        } else {
            Toast.makeText(context, "권한이 거부되었습니다. 서비스가 작동하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val mChannel = NotificationChannel("fg-service", "fg-service", importance)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_DENIED
            ) {
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                checkAndStartService(context)
            }
        } else {
            checkAndStartService(context)
        }
    }

    //UI
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        StartScreen()
    }
}

fun checkAndStartService(context: Context) {
    checkOverlayPermission(context) {
        ContextCompat.startForegroundService(
            context, Intent(context, AppForegroundService::class.java)
        )
        Log.d("MyTag", "서비스 시작")
    }
}

fun checkOverlayPermission(context: Context, onSuccess: () -> Unit) {
    val alarmManager = context.getSystemService(AlarmManager::class.java)
    var isOverlayPermissionGranted = true
    var isAlarmPermissionGranted = true

    // 오버레이 권한 체크
    if (!Settings.canDrawOverlays(context)) {
        isOverlayPermissionGranted = false
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }

    // 알람 및 리마인더 권한 체크
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            isAlarmPermissionGranted = false
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }

    if (isOverlayPermissionGranted && isAlarmPermissionGranted) {
        onSuccess()
    } else {
        // 권한 비동기 처리
        checkPermissionsAsync(context, onSuccess)
    }
}

fun checkPermissionsAsync(context: Context, onSuccess: () -> Unit) {
    val handler = android.os.Handler(Looper.getMainLooper())
    handler.postDelayed(object : Runnable {
        override fun run() {
            if (Settings.canDrawOverlays(context) && (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || context.getSystemService(
                    AlarmManager::class.java
                ).canScheduleExactAlarms())
            ) {
                onSuccess()
            } else {
                handler.postDelayed(this, 100)
            }
        }
    }, 100)
}