package com.project.managelesson.auth.domain.use_case.profile_use_case

import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.use_case.task_use_case.GetAllUpcomingTaskUseCase
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.ZoneId

class GetAllUpcomingTasksByDate(
    private val getAllUpcomingTaskUseCase: GetAllUpcomingTaskUseCase
) {
    suspend operator fun invoke(date: Long?): List<Task> {
        val day = Instant.ofEpochMilli(date!!)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toEpochDay()

        val tasks =  getAllUpcomingTaskUseCase().first()

        return tasks.filter { task ->
            val taskDay = Instant.ofEpochMilli(task.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .toEpochDay()

            println("Task ID: ${task.id}, Date: ${task.date}, Converted Day: $taskDay")
            taskDay == day
        }
    }
}