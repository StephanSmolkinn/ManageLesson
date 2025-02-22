package com.project.managelesson.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.data.data_source.remote.ApiState
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor (
    private val manageLessonUseCase: ManageLessonUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is SignUpEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignUpEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            SignUpEvent.Register -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val user = UserResponse(
                        fullName = state.value.name,
                        email = state.value.email,
                        password = state.value.password
                    )
                    manageLessonUseCase.registerUserUseCase(user).onEach {
                        when(it) {
                            is ApiState.Success -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(message = "Register success"))
                            }
                            is ApiState.Failure -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(message = it.message))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object RegisterSuccess : UiEvent()
        object RegisterError : UiEvent()
    }

}