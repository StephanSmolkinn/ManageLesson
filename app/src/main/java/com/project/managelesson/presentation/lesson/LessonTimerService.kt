package com.project.managelesson.presentation.lesson

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.project.managelesson.utils.Constants.CHANNEL_NOTIFICATION_ID
import com.project.managelesson.utils.Constants.CHANNEL_NOTIFICATION_NAME
import com.project.managelesson.utils.Constants.NOTIFICATION_ID
import com.project.managelesson.utils.Constants.SERVICE_ACTION_CANCEL
import com.project.managelesson.utils.Constants.SERVICE_ACTION_START
import com.project.managelesson.utils.Constants.SERVICE_ACTION_STOP
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class LessonTimerService : Service() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    var seconds = mutableStateOf("00")
        private set

    var minutes = mutableStateOf("00")
        private set

    var hours = mutableStateOf("00")
        private set

    var duration: Duration = Duration.ZERO
        private set

    lateinit var timer: Timer

    var timerState = mutableStateOf(TimerState.Closed)
        private set

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action.let {
            when(it) {
                SERVICE_ACTION_START -> {
                    startForegroundService()
                    startTimer { hour, minute, second ->
                        updateNotify(hour, minute, second)
                    }
                }

                SERVICE_ACTION_STOP -> {
                    stopTimer()
                }

                SERVICE_ACTION_CANCEL -> {
                    stopTimer()
                    cancelTimer()
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_NOTIFICATION_ID,
            CHANNEL_NOTIFICATION_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
        stopSelf()
    }

    private fun updateNotify(hour: String, minute: String, second: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder
                .setContentText("$hour:$minute:$second")
                .build()
        )
    }

    private fun startTimer(
        onTimerTick: (hour: String, minute: String, second: String) -> Unit
    ) {
        timerState.value = TimerState.Start
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTime()
            onTimerTick(hours.value, minutes.value, seconds.value)
        }
    }

    private fun stopTimer() {
        if (this::timer.isInitialized)
            timer.cancel()

        timerState.value = TimerState.Stop
    }

    private fun cancelTimer() {
        duration = ZERO
        updateTime()
        timerState.value = TimerState.Closed
    }

    private fun updateTime() {
        duration.toComponents { hours, minutes, seconds, _ ->
            this@LessonTimerService.hours.value = hours.toInt().toString().padStart(length = 2, padChar = '0')
            this@LessonTimerService.minutes.value = minutes.toString().padStart(length = 2, padChar = '0')
            this@LessonTimerService.seconds.value = seconds.toString().padStart(length = 2, padChar = '0')
        }
    }

}