package com.project.managelesson.auth.presentation.profile.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.project.managelesson.core.utils.millisToDateString
import com.project.managelesson.core.utils.toHours
import com.project.managelesson.study.domain.model.Lesson

fun LazyListScope.lessonCardProfileList(list: List<Lesson>) {
    items(list) {
        LessonCardProfile(
            name = it.relateSubject,
            date = it.date.millisToDateString(),
            duration = "%.2f".format(it.duration.toHours())
        )
    }
}