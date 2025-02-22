package com.project.managelesson

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.project.managelesson.navigation.Navigation
import com.project.managelesson.presentation.lesson.LessonTimerService
import com.project.managelesson.presentation.theme.ManageLessonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (bound.value) {
                ManageLessonTheme {
                    Navigation(timerService)
                }
            }
        }
        requestPermission()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, LessonTimerService::class.java).also {
            bindService(it, connectService, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connectService)
        bound.value = false
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    private var bound = mutableStateOf(false)
    private lateinit var timerService: LessonTimerService

    private val connectService = object  : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LessonTimerService.LessonTimerServiceBinder
            timerService = binder.getService()
            bound.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound.value = false
        }
    }
}
