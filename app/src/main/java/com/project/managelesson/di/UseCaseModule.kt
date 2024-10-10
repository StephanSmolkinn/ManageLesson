package com.project.managelesson.di

import com.project.managelesson.domain.repository.SubjectRepository
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
    fun provideUseCase(subjectRepository: SubjectRepository): ManageLessonUseCase {
        return ManageLessonUseCase(
            upsertSubjectUseCase = UpsertSubjectUseCase(subjectRepository)
        )
    }

}