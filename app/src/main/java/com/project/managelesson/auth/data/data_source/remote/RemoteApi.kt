package com.project.managelesson.auth.data.data_source.remote

import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.auth.domain.parameters.LoginUserParameters
import com.project.managelesson.auth.domain.parameters.RegisterUserParameters
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteApi {
    @POST("/auth/register")
    suspend fun registerUser(@Body registerUserParameters: RegisterUserParameters): Response<User>

    @POST("/auth/login")
    suspend fun loginUser(@Body loginUser: LoginUserParameters): Response<User>
}