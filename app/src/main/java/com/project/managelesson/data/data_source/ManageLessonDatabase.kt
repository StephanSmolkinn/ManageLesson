package com.project.managelesson.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task

@Database(
    entities = [Subject::class, Task::class, Lesson::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class ManageLessonDatabase: RoomDatabase() {

    abstract val subjectDao: SubjectDao

    abstract val taskDao: TaskDao

    abstract val lessonDao: LessonDao

    companion object {
        const val DATABASE_NAME = "manage_lesson_db"
    }

}