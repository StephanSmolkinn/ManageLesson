package com.project.managelesson.data.data_source.remote.responses

data class UserResponse(
    val fullName: String,
    val email: String,
    val password: String,
    val authToken: String? = null
)
