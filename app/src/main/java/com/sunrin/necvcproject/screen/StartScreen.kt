package com.sunrin.necvcproject.screen

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrin.necvcproject.alarm.AlarmUtils
import com.sunrin.necvcproject.component.CustomButton
import com.sunrin.necvcproject.component.StartTitle
import com.sunrin.necvcproject.component.TimePicker
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun StartScreen() {
    val context = LocalContext.current
    val pref = context.getSharedPreferences("alarm", Context.MODE_PRIVATE)
    val (timeInMillis, setTimeInMillis) = remember { mutableStateOf(pref.getLong("initTime",0)) }
    var isEnable by remember { mutableStateOf(!pref.getBoolean("enable",true)) }
    val configuration = LocalConfiguration.current
    var currentTimeInMillis by remember { mutableStateOf(0L) }
    val screenHeight = configuration.screenHeightDp.dp

    // 타이머 동작
    LaunchedEffect(!isEnable) {
        if (!isEnable) {
            while (currentTimeInMillis > 0) {
                delay(1000L) // 1초 대기
                currentTimeInMillis -= 1000L
                Log.d("MyTag", "돌아간다~ $currentTimeInMillis")
                Log.d("MyTag", "고정돼야된다~ 돌아간다~ $timeInMillis")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 22.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            Column {
                Spacer(modifier = Modifier.height(screenHeight / 15))
                StartTitle()
                Spacer(modifier = Modifier.height(screenHeight / 15 / 4))
                TimePicker(
                    onTimeChange = { time ->
                        setTimeInMillis(time)
                    },
                    isEnable,
                    currentTimeInMillis
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            CustomButton(
                text = "집중모드 시작하기",
                todo = {
                    checkOverlayPermission(context)
                    if (!isEnable) {
                        pref.edit().putBoolean("enable", false).apply()
                        val alarmManager = context.getSystemService(AlarmManager::class.java)
                        AlarmUtils.cancelExistAlarm(alarmManager, context)
                        currentTimeInMillis = timeInMillis
                        Log.d("MyTag", "바뀌여야된다~: $currentTimeInMillis")
                    } else {
                        pref.edit().putBoolean("enable", true).putLong("usableTime", timeInMillis)
                            .putLong("initTime", timeInMillis).apply()
                        Log.d("MyTag", "timeInMillis: $timeInMillis")
                        context.sendBroadcast(Intent("ALARM_RESTART"))
                        currentTimeInMillis = timeInMillis
                    }
                    isEnable = !isEnable
                },
                isEnable = isEnable
            )
        }
    }
}
fun checkOverlayPermission(context: Context) {
    // 오버레이 권한 체크
    if (!Settings.canDrawOverlays(context)) {
        // 권한이 없으면 사용자에게 요청
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    } else {
        // 권한이 이미 있을 경우
    }
}