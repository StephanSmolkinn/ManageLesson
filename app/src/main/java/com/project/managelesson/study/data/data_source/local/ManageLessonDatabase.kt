package com.project.managelesson.study.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.managelesson.core.data.data_model.UserEntity
import com.project.managelesson.core.data.data_source.UserDao
import com.project.managelesson.study.data.model.LessonEntity
import com.project.managelesson.study.data.model.SubjectEntity
import com.project.managelesson.study.data.model.TaskEntity

@Database(
    entities = [SubjectEntity::class, TaskEntity::class, LessonEntity::class, UserEntity::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class ManageLessonDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun lessonDao(): LessonDao

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "manage_lesson_db"
    }

}