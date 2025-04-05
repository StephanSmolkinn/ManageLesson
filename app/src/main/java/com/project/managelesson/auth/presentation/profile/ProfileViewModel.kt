package com.project.managelesson.auth.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.core.domain.use_cases.ManageLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            getUser()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            ProfileState()
        )

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogout -> {
                viewModelScope.launch(Dispatchers.IO) {

                }
            }

            is ProfileEvent.OnTaskChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    manageLessonUseCase.upsertTaskUseCase(
                        task = event.task.copy(isCompleted = !event.task.isCompleted)
                    )
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }

            is ProfileEvent.OnDateChange -> {
                _state.update {
                    it.copy(
                        selectedDate = event.date
                    )
                }
                viewModelScope.launch {
                    manageLessonUseCase.getAllUpcomingTasksByDate(event.date).also { list ->
                        _state.update {
                            it.copy(
                                tasksByDate = list
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            manageLessonUseCase.getUserUseCase()?.let { user ->
                _state.update {
                    it.copy(
                        user = user,
                        isLoading = false
                    )
                }
            }
        }
    }

}