package com.project.managelesson.core.di

import com.project.managelesson.study.data.repository.LessonRepositoryImpl
import com.project.managelesson.study.data.repository.SubjectRepositoryImpl
import com.project.managelesson.study.data.repository.TaskRepositoryImpl
import com.project.managelesson.auth.data.repository.UserRemoteRepositoryImpl
import com.project.managelesson.core.data.repository.UserRepositoryImpl
import com.project.managelesson.study.domain.repository.LessonRepository
import com.project.managelesson.study.domain.repository.SubjectRepository
import com.project.managelesson.study.domain.repository.TaskRepository
import com.project.managelesson.auth.domain.repository.UserRemoteRepository
import com.project.managelesson.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
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

    @Singleton
    @Binds
    abstract fun bindRemoteApiRepository(
        userRemoteRepositoryImpl: UserRemoteRepositoryImpl
    ): UserRemoteRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

}