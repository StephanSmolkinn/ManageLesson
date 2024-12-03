package com.project.managelesson.presentation.subject

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.domain.model.InvalidSubjectException
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import com.project.managelesson.utils.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val subjectId = savedStateHandle.get<Int>("subjectId") ?: -1

    private val _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        manageLessonUseCase.getUpcomingTaskBySubjectUseCase(subjectId),
        manageLessonUseCase.getCompleteTaskBySubjectUseCase(subjectId),
        manageLessonUseCase.getRecentTenLessonBySubjectUseCase(subjectId),
        manageLessonUseCase.getSumDurationBySubjectUseCase(subjectId)
    ) { state, upcomingTasks, completeTasks, recentTenLesson, getSumSLessonDuration ->
        state.copy(
            upcomingTaskList = upcomingTasks,
            completedTaskList = completeTasks,
            recentLessonList = recentTenLesson,
            studyHours = getSumSLessonDuration.toHours()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), SubjectState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getSubject()
    }

    fun onEvent(event: SubjectEvent) {
        when (event) {
            SubjectEvent.UpdateSubject -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.upsertSubjectUseCase(
                            Subject(
                                id = subjectId,
                                title = state.value.subjectName,
                                goalHours = state.value.goalLessonHours.toDouble(),
                                color = state.value.cardColors.map { it.toArgb() }
                            )
                        )
                        _eventFlow.emit(UiEvent.UpdateSubject)
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Subject has been updated")
                        )
                    } catch (e: InvalidSubjectException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = e.message ?: "Can not update subject")
                        )

                    } catch (e: NumberFormatException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Invalid number")
                        )
                    }
                }
            }

            SubjectEvent.UpdateProgress -> {
                val goalHours = state.value.goalLessonHours.toDoubleOrNull() ?: 1.0
                _state.update {
                    it.copy(
                        progress = (state.value.studyHours / goalHours).coerceIn(0.0, 1.0)
                    )
                }
            }

            SubjectEvent.DeleteLesson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        state.value.lesson?.let {
                            manageLessonUseCase.deleteLessonUseCase(it)
                        }
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Lesson has been deleted"))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not delete lesson"))
                    }
                }
            }

            SubjectEvent.DeleteSubject -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        state.value.subjectId?.let {
                            manageLessonUseCase.deleteSubjectUseCase(it)
                        }
                        _eventFlow.emit(UiEvent.DeleteSubject)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not delete subject"))
                    }
                }
            }

            is SubjectEvent.OnDeleteLesson -> {
                _state.update {
                    it.copy(lesson = event.lesson)
                }
            }

            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(cardColors = event.color)
                }
            }

            is SubjectEvent.OnSubjectGoalHourChange -> {
                _state.update {
                    it.copy(goalLessonHours = event.hours)
                }
            }

            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }

            is SubjectEvent.OnTaskCompleteChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.upsertTaskUseCase(
                            task = event.task.copy(isCompleted = !event.task.isCompleted)
                        )
                        val message = if (!event.task.isCompleted) "Saved in completed tasks" else "Saved in upcoming tasks"
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = message))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not complete task"))
                    }
                }
            }
        }
    }

    private fun getSubject() {
        viewModelScope.launch(Dispatchers.IO) {
            manageLessonUseCase.getSubjectByIdUseCase(subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                        subjectName = subject.title,
                        goalLessonHours = subject.goalHours.toString(),
                        cardColors = subject.color.map { Color(it) },
                        subjectId = subject.id
                    )
                }
            }
        }
    }

    sealed class UiEvent {
        data object UpdateSubject : UiEvent()
        data object DeleteSubject : UiEvent()
        data class ShowSnackBar(val message: String) : UiEvent()
    }

}