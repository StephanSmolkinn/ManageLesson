package com.project.managelesson.core.di

import android.app.Application
import androidx.room.Room
import com.project.managelesson.study.data.data_source.local.LessonDao
import com.project.managelesson.study.data.data_source.local.ManageLessonDatabase
import com.project.managelesson.study.data.data_source.local.SubjectDao
import com.project.managelesson.study.data.data_source.local.TaskDao
import com.project.managelesson.core.data.data_source.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Volatile
    private var _instance: ManageLessonDatabase? = null

    @Provides
    @Singleton
    fun provideManageLessonDatabase(application: Application): ManageLessonDatabase {
        return _instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                    application,
                    ManageLessonDatabase::class.java,
                    ManageLessonDatabase.DATABASE_NAME
                ).build()
            _instance = instance
            instance
        }
    }

    @Provides
    @Singleton
    fun provideSubjectDao(db: ManageLessonDatabase): SubjectDao {
        return db.subjectDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: ManageLessonDatabase): TaskDao {
        return db.taskDao()
    }

    @Provides
    @Singleton
    fun provideLessonDao(db: ManageLessonDatabase): LessonDao {
        return db.lessonDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: ManageLessonDatabase): UserDao {
        return db.userDao()
    }

}