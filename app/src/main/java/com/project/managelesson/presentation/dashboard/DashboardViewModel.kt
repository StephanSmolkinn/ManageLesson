package com.project.managelesson.presentation.dashboard

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.domain.model.InvalidSubjectException
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import com.project.managelesson.utils.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        manageLessonUseCase.getSubjectCountUseCase(),
        manageLessonUseCase.getSubjectHoursUseCase(),
        manageLessonUseCase.getSubjectsUseCase(),
        manageLessonUseCase.getSumDurationUseCase()
    ) { state, subjectCount, goalHours, subjects, sumLessonDuration ->
        state.copy(
            totalSubjectCount = subjectCount,
            totalLessonGoalHours = goalHours,
            subjects = subjects,
            totalLessonHours = sumLessonDuration.toHours()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), DashboardState())

    val taskList: StateFlow<List<Task>> = manageLessonUseCase.getAllUpcomingTaskUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    val lessonList: StateFlow<List<Lesson>> = manageLessonUseCase.getRecentFiveLessonUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.SaveSubject -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.upsertSubjectUseCase(
                            Subject(
                                title = state.value.subjectName,
                                goalHours = state.value.goalLessonHours.toDouble(),
                                color = state.value.subjectCardColor.map { it.toArgb() }
                            )
                        )
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Subject ${state.value.subjectName} has been created")
                        )
                    } catch (e: InvalidSubjectException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = e.message ?: "Can not add subject")
                        )
                    } catch (e: NumberFormatException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Invalid number")
                        )
                    } finally {
                        _state.update {
                            it.copy(
                                subjectName = "",
                                goalLessonHours = "",
                                subjectCardColor = Subject.subjectColor.random()
                            )
                        }
                    }
                }
            }

            DashboardEvent.DeleteLesson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        state.value.lesson?.let {
                            manageLessonUseCase.deleteLessonUseCase(it)
                        }
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Lesson deleted"))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not delete lesson"))
                    }
                }
            }

            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }

            is DashboardEvent.OnSubjectHoursChange -> {
                _state.update {
                    it.copy(goalLessonHours = event.hours)
                }
            }

            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColor = event.colors)
                }
            }

            is DashboardEvent.OnDeleteLesson -> {
                _state.update {
                    it.copy(lesson = event.lesson)
                }
            }

            is DashboardEvent.OnTaskChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.upsertTaskUseCase(
                            task = event.task.copy(isCompleted = !event.task.isCompleted)
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Saved in completed tasks"))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not complete task"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }

}