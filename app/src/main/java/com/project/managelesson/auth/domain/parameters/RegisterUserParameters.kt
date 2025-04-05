package com.project.managelesson.auth.domain.parameters

data class RegisterUserParameters(
    val fullName: String,
    val email: String,
    val password: String,
)