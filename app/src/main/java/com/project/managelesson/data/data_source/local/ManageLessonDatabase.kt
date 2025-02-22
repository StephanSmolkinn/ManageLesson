package com.project.managelesson.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.model.User

@Database(
    entities = [Subject::class, Task::class, Lesson::class, User::class],
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