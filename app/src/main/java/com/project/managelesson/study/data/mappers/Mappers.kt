package com.project.managelesson.study.data.mappers

import com.project.managelesson.study.data.model.LessonEntity
import com.project.managelesson.study.data.model.SubjectEntity
import com.project.managelesson.study.data.model.TaskEntity
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.domain.model.Task

fun Subject.toSubject(): SubjectEntity {
    return SubjectEntity(
        id = id,
        title = title,
        goalHours = goalHours,
        color = color
    )
}

fun SubjectEntity.toDomainSubject(): Subject {
    return Subject(
        id = id,
        title = title,
        goalHours = goalHours,
        color = color
    )
}

fun Lesson.toLesson(): LessonEntity {
    return LessonEntity(
        id = id,
        date = date,
        duration = duration,
        relateSubject = relateSubject,
        subjectId = subjectId
    )
}

fun LessonEntity.toDomainLesson(): Lesson {
    return Lesson(
        id = id,
        date = date,
        duration = duration,
        relateSubject = relateSubject,
        subjectId = subjectId
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        date = date,
        referToSubject = referToSubject,
        isCompleted = isCompleted,
        subjectId = subjectId
    )
}

fun TaskEntity.toTaskDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        date = date,
        referToSubject = referToSubject,
        isCompleted = isCompleted,
        subjectId = subjectId
    )
}

