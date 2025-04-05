package com.project.managelesson.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.auth.data.data_source.remote.ApiState
import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.auth.domain.parameters.LoginUserParameters
import com.project.managelesson.core.domain.use_cases.ManageLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val state = _state
        .onStart {
            getUser()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is SignInEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            SignInEvent.OnAuth -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val user = LoginUserParameters(state.value.email, state.value.password)
                    manageLessonUseCase.loginUserUseCase(user).onEach {
                        when (it) {
                            is ApiState.Success -> {
                                upsertUser(it.data)
                                _eventFlow.emit(UiEvent.ShowSnackBar(message = "Success login"))
                                _eventFlow.emit(UiEvent.AuthSuccess)
                            }
                            is ApiState.Failure -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(message = it.error.name))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private suspend fun upsertUser(user: User) {
        val newUser = User(
            id = 1,
            fullName = user.fullName,
            email = user.email,
            authToken = user.authToken
        )
        manageLessonUseCase.upsertUserUseCase(newUser)
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            manageLessonUseCase.getUserUseCase()?.let { user ->
                _state.update {
                    it.copy(
                        email = user.email,
                        isUserExist = true
                    )
                }
                _eventFlow.emit(UiEvent.AuthSuccess)
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object AuthSuccess : UiEvent()
        object AuthError : UiEvent()
    }

}