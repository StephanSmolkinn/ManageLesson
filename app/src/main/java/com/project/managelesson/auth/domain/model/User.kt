package com.project.managelesson.auth.domain.model

data class User(
    val id: Int? = null,
    val fullName: String,
    val email: String,
    val authToken: String? = null
)
