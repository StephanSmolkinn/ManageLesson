package com.project.managelesson.auth.presentation.sign_up

sealed class SignUpEvent {
    data class OnNameChange(val name: String) : SignUpEvent()
    data class OnEmailChange(val email: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    data object OnRegister : SignUpEvent()
}