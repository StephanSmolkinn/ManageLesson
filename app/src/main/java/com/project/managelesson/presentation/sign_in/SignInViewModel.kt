package com.project.managelesson.presentation.sign_in

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.managelesson.data.data_source.remote.ApiState
import com.project.managelesson.data.data_source.remote.LoginUserParameters
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import com.project.managelesson.domain.model.User
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.Parcelize
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
class SignInViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getUser()
    }

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

            SignInEvent.Auth -> {
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
                                _eventFlow.emit(UiEvent.ShowSnackBar(message = it.message))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private suspend fun upsertUser(userResponse: UserResponse) {
        val user = User(
            id = 1,
            fullName = userResponse.fullName,
            email = userResponse.email,
            authToken = userResponse.authToken
        )
        manageLessonUseCase.upsertUserUseCase(user)
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
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object AuthSuccess : UiEvent()
        object AuthError : UiEvent()
    }

    @Parcelize
    data class User(
        val name: String,
        val email: String,
        val password: String,
    ) : Parcelable

}