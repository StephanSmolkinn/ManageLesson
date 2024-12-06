package com.project.managelesson.data.work_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.managelesson.R
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskNotifyWorker @AssistedInject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        Log.d("TaskNotifyWorker", "doWork() викликано")
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
            .setContentTitle("Завдання на завтра")
            .setContentText("У вас є завдань, які потрібно виконати завтра.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "TASK_CHANNEL",
                "Завдання",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Сповіщення про завдання на завтра"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
