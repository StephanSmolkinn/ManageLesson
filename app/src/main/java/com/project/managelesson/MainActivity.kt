package com.project.managelesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.navigation.Navigation
import com.project.managelesson.presentation.dashboard.DashboardScreen
import com.project.managelesson.presentation.lesson.LessonScreen
import com.project.managelesson.presentation.subject.SubjectScreen
import com.project.managelesson.presentation.task.TaskScreen
import com.project.managelesson.presentation.theme.ManageLessonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageLessonTheme {
                Navigation()
            }
        }
    }
}

val test = listOf(
    Subject(0, "Physics", 12.0, color = Subject.subjectColor[0],),
    Subject(1,"Maths", 12.6, color = Subject.subjectColor[1]),
    Subject(2,"English", 22.9, color = Subject.subjectColor[2]),
    Subject(3,"Geology", 9.0, color = Subject.subjectColor[3]),
    Subject(4,"Physics", 52.0, color = Subject.subjectColor[4])
)

val tasks = listOf(
    Task(1, title = "Task23232", "Do something good", 0, 0L, "", true, 0),
    Task(1,title = "Task4343", "Do something good", 1, 0L, "", false, 0),
    Task(1,title = "Task", "Do something good", 1, 0L, "", true, 0),
    Task(1,title = "Task", "Do something good", 2, 0L, "", false, 0),
    Task(1,title = "Task435454", "Do something good", 0, 0L, "", true, 0),
    Task(1,title = "Task", "Do something good", 2, 0L, "", true, 0),
    Task(1,title = "Task5543", "Do something good", 0, 0L, "", true, 0),
    Task(1, title = "Task", "Do something good", 2, 0L, "", true, 0)
)

val lessons = listOf(
    Lesson(1, 0L, 2L, "Physics", 0),
    Lesson(1, 0L, 0L, "Maths", 0),
    Lesson(1, 0L, 3L, "Physics", 0),
    Lesson(1, 0L, 0L, "Geology", 0),
    Lesson(1, 0L, 2L, "Physics", 0),
    Lesson(1, 0L, 5L, "English", 0)
)
