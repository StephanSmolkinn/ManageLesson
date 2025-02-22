package com.project.managelesson.presentation.sign_in

sealed class SignInEvent {
    data class OnEmailChange(val email: String) : SignInEvent()
    data class OnPasswordChange(val password: String) : SignInEvent()
    object Auth : SignInEvent()
}