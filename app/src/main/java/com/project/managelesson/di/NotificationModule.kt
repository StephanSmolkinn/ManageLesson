package com.project.managelesson.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.project.managelesson.R
import com.project.managelesson.presentation.lesson.ServiceHelper
import com.project.managelesson.utils.Constants.CHANNEL_NOTIFICATION_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {
    @ServiceScoped
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat
            .Builder(
                context,
                CHANNEL_NOTIFICATION_ID
            )
            .setContentTitle("Lesson")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setContentIntent(ServiceHelper.clickIntent(context))
    }
}