package com.project.managelesson.presentation.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.use_case.ManageLessonUseCase
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LessonState())
    val state = combine(
        _state,
        manageLessonUseCase.getSubjectsUseCase(),
        manageLessonUseCase.getLessonsUseCase()
    ) { state, subjects, lessons ->
        state.copy(
            subjectList = subjects,
            lessonList = lessons
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), LessonState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LessonEvent) {
        when(event) {
            is LessonEvent.OnDeleteLesson -> {
                _state.update {
                    it.copy(lesson = event.lesson)
                }
            }

            is LessonEvent.OnRelateSubject -> {
                _state.update {
                    it.copy(
                        relateSubject = event.subject.title,
                        subjectId = event.subject.id
                    )
                }
            }

            is LessonEvent.SaveLesson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.insertLessonUseCase(
                            Lesson(
                                duration = event.duration,
                                relateSubject = state.value.relateSubject ?: "",
                                date = Instant.now().toEpochMilli(),
                                subjectId = state.value.subjectId ?: -1,
                            )
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Lesson has been saved"))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not save lesson"))
                    }
                }
            }

            is LessonEvent.UpdateSubject -> {
                _state.update {
                    it.copy(
                        subjectId = event.id,
                        relateSubject = event.relateSubject
                    )
                }
            }

            LessonEvent.SubjectId -> {

            }

            LessonEvent.DeleteLesson -> {
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
        }
    }

    fun startLesson(start: () -> Unit, timerState: TimerState) {
        if (state.value.subjectId != null && state.value.relateSubject != null) {
            start()
        } else if (timerState != TimerState.Start) {
            viewModelScope.launch(Dispatchers.IO) {
                _eventFlow.emit(UiEvent.ShowSnackBar(message = "Select relate subject"))
            }
        } else {
            start()
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }

}