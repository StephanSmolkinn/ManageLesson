package com.project.managelesson

import android.app.Application
import androidx.work.Configuration
import com.project.managelesson.data.work_manager.TaskNotifyWorkerFactory
import com.project.managelesson.data.work_manager.WorkManagerHelper
import dagger.hilt.android.HiltAndroidApp

import javax.inject.Inject

@HiltAndroidApp
class ManageLessonApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: TaskNotifyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        WorkManagerHelper.scheduleDailyTaskNotification(this)
    }

}