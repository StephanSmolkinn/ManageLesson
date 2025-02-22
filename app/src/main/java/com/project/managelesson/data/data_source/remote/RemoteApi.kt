package com.project.managelesson.data.data_source.remote

import com.project.managelesson.data.data_source.remote.responses.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteApi {
    @POST("/auth/register")
    suspend fun registerUser(@Body userResponse: UserResponse): Response<UserResponse>

    @POST("/auth/login")
    suspend fun loginUser(@Body loginUser: LoginUserParameters): Response<UserResponse>
}