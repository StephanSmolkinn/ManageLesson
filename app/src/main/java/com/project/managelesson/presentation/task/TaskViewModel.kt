package com.project.managelesson.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.domain.model.InvalidTaskException
import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import com.project.managelesson.utils.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
class TaskViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId = savedStateHandle.get<Int>("taskId") ?: -1
    private val subjectId = savedStateHandle.get<Int>("taskSubjectId") ?: -1

    private val _state = MutableStateFlow(TaskState())
    val state = combine(
        _state,
        manageLessonUseCase.getSubjectsUseCase()
    ) { state, subjectList ->
        state.copy(
            subjectList = subjectList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), TaskState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getTask()
        getSubject()
    }

    fun onEvent(event: TaskEvent) {
        when(event) {
            TaskEvent.SaveTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        manageLessonUseCase.upsertTaskUseCase(
                            Task(
                                id = if (taskId != -1) taskId else _state.value.taskId,
                                title = _state.value.taskName,
                                description = _state.value.description,
                                priority = _state.value.priority.value,
                                date = _state.value.date ?: Instant.now().toEpochMilli(),
                                referToSubject = _state.value.relateSubject ?: return@launch,
                                isCompleted = _state.value.isTaskComplete,
                                subjectId = _state.value.subjectId ?: return@launch
                            )
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Task was saved"))
                        delay(3000L)
                        _eventFlow.emit(UiEvent.SaveTask)
                    } catch (e: InvalidTaskException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Can not save task"))
                    }
                }
            }

            TaskEvent.DeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        state.value.taskId?.let {
                            manageLessonUseCase.deleteTaskUseCase(it)
                        }
                        _eventFlow.emit(UiEvent.DeleteTask)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = "Can not delete task"))
                    }
                }
            }

            TaskEvent.OnChangeComplete -> {
                _state.update {
                    it.copy(isTaskComplete = !_state.value.isTaskComplete)
                }
            }

            is TaskEvent.OnTaskNameChange -> {
                _state.update {
                    it.copy(taskName = event.name)
                }
            }

            is TaskEvent.OnTaskDescriptionChange -> {
                _state.update {
                    it.copy(description = event.description)
                }
            }

            is TaskEvent.OnDateChange -> {
                _state.update {
                    it.copy(date = event.date)
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(priority = event.priority)
                }
            }

            is TaskEvent.OnRelateSubject -> {
                _state.update {
                    it.copy(
                        subjectId = event.subject.id,
                        relateSubject = event.subject.title
                    )
                }
            }
        }
    }

    private fun getTask() {
        viewModelScope.launch(Dispatchers.IO) {
            manageLessonUseCase.getTaskUseCase(taskId)?.let { task ->
                _state.update {
                    it.copy(
                        taskId = task.id,
                        taskName = task.title,
                        description = task.description,
                        date = task.date,
                        isTaskComplete = task.isCompleted,
                        priority = Priority.takePriority(task.priority),
                        relateSubject = task.referToSubject,
                        subjectId = task.subjectId,
                    )
                }
            }
        }
    }

    private fun getSubject() {
        viewModelScope.launch(Dispatchers.IO) {
            manageLessonUseCase.getSubjectByIdUseCase(subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                        relateSubject = subject.title,
                        subjectId = subject.id
                    )
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveTask : UiEvent()
        data object DeleteTask : UiEvent()
        data class ShowSnackBar(val message: String) : UiEvent()
    }

}