package com.project.managelesson.study.data.work_manager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.project.managelesson.core.domain.use_cases.ManageLessonUseCase
import javax.inject.Inject

class TaskNotifyWorkerFactory @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = TaskNotifyWorker(manageLessonUseCase, appContext, workerParameters)
}