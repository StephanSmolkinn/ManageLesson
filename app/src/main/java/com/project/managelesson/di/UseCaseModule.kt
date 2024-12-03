package com.project.managelesson.di

import com.project.managelesson.domain.repository.LessonRepository
import com.project.managelesson.domain.repository.SubjectRepository
import com.project.managelesson.domain.repository.TaskRepository
import com.project.managelesson.domain.use_case.lesson_use_case.DeleteLessonUseCase
import com.project.managelesson.domain.use_case.subject_use_case.DeleteSubjectUseCase
import com.project.managelesson.domain.use_case.task_use_case.DeleteTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetAllUpcomingTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetCompleteTaskBySubjectUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetLessonsUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetRecentFiveLessonUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetRecentTenLessonBySubjectUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectByIdUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectCountUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectHoursUseCase
import com.project.managelesson.domain.use_case.subject_use_case.GetSubjectsUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetSumDurationBySubjectUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.GetSumDurationUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetTaskUseCase
import com.project.managelesson.domain.use_case.task_use_case.GetUpcomingTaskBySubjectUseCase
import com.project.managelesson.domain.use_case.lesson_use_case.InsertLessonUseCase
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import com.project.managelesson.domain.use_case.subject_use_case.UpsertSubjectUseCase
import com.project.managelesson.domain.use_case.task_use_case.UpsertTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(
        subjectRepository: SubjectRepository,
        lessonRepository: LessonRepository,
        taskRepository: TaskRepository
    ): ManageLessonUseCase {
        return ManageLessonUseCase(
            upsertSubjectUseCase = UpsertSubjectUseCase(subjectRepository),
            getSubjectCountUseCase = GetSubjectCountUseCase(subjectRepository),
            getSubjectHoursUseCase = GetSubjectHoursUseCase(subjectRepository),
            getSubjectByIdUseCase = GetSubjectByIdUseCase(subjectRepository),
            getSubjectsUseCase = GetSubjectsUseCase(subjectRepository),
            deleteSubjectUseCase = DeleteSubjectUseCase(subjectRepository),
            insertLessonUseCase = InsertLessonUseCase(lessonRepository),
            getSumDurationUseCase = GetSumDurationUseCase(lessonRepository),
            getAllUpcomingTaskUseCase = GetAllUpcomingTaskUseCase(taskRepository),
            getRecentFiveLessonUseCase = GetRecentFiveLessonUseCase(lessonRepository),
            getUpcomingTaskBySubjectUseCase = GetUpcomingTaskBySubjectUseCase(taskRepository),
            getCompleteTaskBySubjectUseCase = GetCompleteTaskBySubjectUseCase(taskRepository),
            getRecentTenLessonBySubjectUseCase = GetRecentTenLessonBySubjectUseCase(lessonRepository),
            getSumDurationBySubjectUseCase = GetSumDurationBySubjectUseCase(lessonRepository),
            upsertTaskUseCase = UpsertTaskUseCase(taskRepository),
            getTaskUseCase = GetTaskUseCase(taskRepository),
            deleteTaskUseCase = DeleteTaskUseCase(taskRepository),
            getLessonsUseCase = GetLessonsUseCase(lessonRepository),
            deleteLessonUseCase = DeleteLessonUseCase(lessonRepository)
        )
    }

}