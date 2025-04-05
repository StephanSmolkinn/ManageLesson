package com.project.managelesson.auth.data.repository

import com.project.managelesson.auth.data.data_source.remote.RemoteApi
import com.project.managelesson.auth.domain.parameters.LoginUserParameters
import com.project.managelesson.auth.domain.parameters.RegisterUserParameters
import com.project.managelesson.auth.domain.repository.UserRemoteRepository
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi
) : UserRemoteRepository {
    override suspend fun registerUser(registerUser: RegisterUserParameters) =
        remoteApi.registerUser(registerUser)

    override suspend fun loginUser(loginUser: LoginUserParameters) = remoteApi.loginUser(loginUser)
}
