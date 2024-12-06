package com.project.managelesson.domain.use_case.task_notify_use_case

import com.project.managelesson.domain.use_case.task_use_case.GetAllUpcomingTaskUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class FindTaskDateTomorrowUseCase(
    private val getAllUpcomingTaskUseCase: GetAllUpcomingTaskUseCase
) {
    suspend operator fun invoke(): Boolean {
        val tomorrowEpochDay = LocalDate.now().plusDays(1).toEpochDay()
        return getAllUpcomingTaskUseCase().map { tasks ->
            tasks.any {
                val taskDateEpochDay =
                    Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                        .toEpochDay()

                taskDateEpochDay == tomorrowEpochDay
            }
        }.first()
    }
}