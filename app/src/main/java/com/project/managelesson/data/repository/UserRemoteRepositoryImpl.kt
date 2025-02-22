package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.remote.LoginUserParameters
import com.project.managelesson.data.data_source.remote.RemoteApi
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import com.project.managelesson.domain.repository.UserRemoteRepository
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi
) : UserRemoteRepository {
    override suspend fun registerUser(userResponse: UserResponse) = remoteApi.registerUser(userResponse)

    override suspend fun loginUser(loginUser: LoginUserParameters) = remoteApi.loginUser(loginUser)
}
