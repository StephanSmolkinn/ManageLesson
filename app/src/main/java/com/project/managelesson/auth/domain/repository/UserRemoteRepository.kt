package com.project.managelesson.auth.domain.repository

import com.project.managelesson.auth.domain.parameters.LoginUserParameters
import com.project.managelesson.auth.domain.parameters.RegisterUserParameters
import com.project.managelesson.auth.domain.model.User
import retrofit2.Response

interface UserRemoteRepository {
    suspend fun registerUser(registerUser: RegisterUserParameters): Response<User>

    suspend fun loginUser(loginUser: LoginUserParameters): Response<User>
}