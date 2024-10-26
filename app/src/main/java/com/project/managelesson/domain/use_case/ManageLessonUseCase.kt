package com.project.managelesson.domain.use_case

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
    val deleteTaskUseCase: DeleteTaskUseCase
)
