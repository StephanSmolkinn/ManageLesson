package com.project.managelesson.presentation.sign_in

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isUserExist: Boolean = false
)