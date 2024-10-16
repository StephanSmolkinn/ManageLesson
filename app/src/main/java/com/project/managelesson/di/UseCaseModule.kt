package com.project.managelesson.di

import com.project.managelesson.domain.repository.LessonRepository
import com.project.managelesson.domain.repository.SubjectRepository
import com.project.managelesson.domain.repository.TaskRepository
import com.project.managelesson.domain.use_case.DeleteSubjectUseCase
import com.project.managelesson.domain.use_case.GetAllUpcomingTaskUseCase
import com.project.managelesson.domain.use_case.GetRecentFiveLessonUseCase
import com.project.managelesson.domain.use_case.GetSubjectByIdUseCase
import com.project.managelesson.domain.use_case.GetSubjectCountUseCase
import com.project.managelesson.domain.use_case.GetSubjectHoursUseCase
import com.project.managelesson.domain.use_case.GetSubjectsUseCase
import com.project.managelesson.domain.use_case.GetSumDurationUseCase
import com.project.managelesson.domain.use_case.InsertLessonUseCase
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import com.project.managelesson.domain.use_case.UpsertSubjectUseCase
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
            getRecentFiveLessonUseCase = GetRecentFiveLessonUseCase(lessonRepository)
        )
    }

}