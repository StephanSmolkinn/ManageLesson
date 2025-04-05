package com.project.managelesson.study.domain.use_case.task_notify_use_case

import com.project.managelesson.core.domain.use_cases.user_use_case.GetUserUseCase
import com.project.managelesson.study.domain.use_case.task_use_case.GetAllUpcomingTaskUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class FindTaskDateTomorrowUseCase(
    private val getAllUpcomingTaskUseCase: GetAllUpcomingTaskUseCase,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Boolean {
        when {
            getUserUseCase() == null -> return false

            else -> {
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
    }
}