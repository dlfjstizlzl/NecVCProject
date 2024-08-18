package com.sunrin.necvcproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sunrin.necvcproject.alarm.AppForegroundService
import com.sunrin.necvcproject.screen.StartScreen
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NecVCProjectTheme {
                Scaffold(
                    topBar = {}
                ){ innerpadding ->
                    Greeting(innerpadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(innerpadding: PaddingValues) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val mChannel = NotificationChannel("fg-service", "fg-service", importance)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)

    Settings.canDrawOverlays(context)

    Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.fromParts("package", "PACKAGE_NAME", null)
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        ContextCompat.startForegroundService(
            context,
            Intent(context, AppForegroundService::class.java)
        )

    }
    //UI
    Surface(
        modifier = Modifier.fillMaxSize().padding(innerpadding)
    ){
        StartScreen()
    }
}