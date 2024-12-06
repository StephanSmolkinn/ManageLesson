package com.project.managelesson.domain.use_case.service_use_case

import android.app.NotificationChannel
import android.app.NotificationManager
import com.project.managelesson.utils.Constants.CHANNEL_NOTIFICATION_ID
import com.project.managelesson.utils.Constants.CHANNEL_NOTIFICATION_NAME

class CreateNotificationChannelUseCase {
    operator fun invoke() {
        val channel = NotificationChannel(
            CHANNEL_NOTIFICATION_ID,
            CHANNEL_NOTIFICATION_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
    }
}