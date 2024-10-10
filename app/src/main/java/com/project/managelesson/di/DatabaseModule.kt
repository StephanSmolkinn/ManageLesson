package com.project.managelesson.di

import android.app.Application
import androidx.room.Room
import com.project.managelesson.data.data_source.ManageLessonDatabase
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

}