package com.project.managelesson.di

import com.project.managelesson.data.repository.LessonRepositoryImpl
import com.project.managelesson.data.repository.SubjectRepositoryImpl
import com.project.managelesson.data.repository.TaskRepositoryImpl
import com.project.managelesson.domain.repository.LessonRepository
import com.project.managelesson.domain.repository.SubjectRepository
import com.project.managelesson.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        subjectRepositoryImpl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindLessonRepository(
        lessonRepositoryImpl: LessonRepositoryImpl
    ): LessonRepository

}