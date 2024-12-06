package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.use_case.lesson_use_case.DeleteLessonUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetLessonsUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetRecentFiveLessonUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetRecentTenLessonBySubjectUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetSumDurationBySubjectUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetSumDurationUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.InsertLessonUseCase
import com.project.managelesson.domain.use_case.subject_use_case.DeleteSubjectUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectByIdUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectCountUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectHoursUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectsUseCase
import com.project.managelesson.domain.use_case.subject_use_case.UpsertSubjectUseCase
import com.project.managelesson.domain.use_case.task_notify_use_case.FindTaskDateTomorrowUseCase
import com.project.managelesson.domain.use_case.task_use_case.DeleteTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetAllUpcomingTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetCompleteTaskBySubjectUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetUpcomingTaskBySubjectUseCase
import com.project.managelesson.domain.use_case.task_use_case.UpsertTaskUseCase

data class ManageLessonUseCase(
    val upsertSubjectUseCase: UpsertSubjectUseCase,
    val getSubjectCountUseCase: GetSubjectCountUseCase,
    val getSubjectHoursUseCase: GetSubjectHoursUseCase,
    val getSubjectByIdUseCase: GetSubjectByIdUseCase,
    val getSubjectsUseCase: GetSubjectsUseCase,
    val deleteSubjectUseCase: DeleteSubjectUseCase,
    val insertLessonUseCase: InsertLessonUseCase,
    val getSumDurationUseCase: GetSumDurationUseCase,
    val getAllUpcomingTaskUseCase: GetAllUpcomingTaskUseCase,
    val getRecentFiveLessonUseCase: GetRecentFiveLessonUseCase,
    val getUpcomingTaskBySubjectUseCase: GetUpcomingTaskBySubjectUseCase,
    val getCompleteTaskBySubjectUseCase: GetCompleteTaskBySubjectUseCase,
    val getRecentTenLessonBySubjectUseCase: GetRecentTenLessonBySubjectUseCase,
    val getSumDurationBySubjectUseCase: GetSumDurationBySubjectUseCase,
    val upsertTaskUseCase: UpsertTaskUseCase,
    val getTaskUseCase: GetTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getLessonsUseCase: GetLessonsUseCase,
    val deleteLessonUseCase: DeleteLessonUseCase,
    val findTaskDateTomorrow: FindTaskDateTomorrowUseCase,
)
