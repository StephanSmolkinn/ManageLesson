package com.project.managelesson.study.data.work_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.managelesson.R
import com.project.managelesson.core.domain.use_cases.ManageLessonUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskNotifyWorker @AssistedInject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val hasTomorrowTask = manageLessonUseCase.findTaskDateTomorrow()
        if (hasTomorrowTask) {
            sendNotification(applicationContext)
        }
        return Result.success()
    }

    private fun sendNotification(context: Context) {
        createNotificationChannel(context)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "TASK_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task for tomorrow")
            .setContentText("You have a task that needs to be completed tomorrow")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "TASK_CHANNEL",
                "Task",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notification for task tomorrow"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
