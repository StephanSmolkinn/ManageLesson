package com.project.managelesson.domain.repository

import com.project.managelesson.data.data_source.remote.LoginUserParameters
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import retrofit2.Response

interface UserRemoteRepository {
    suspend fun registerUser(userResponse: UserResponse): Response<UserResponse>

    suspend fun loginUser(loginUser: LoginUserParameters): Response<UserResponse>
}