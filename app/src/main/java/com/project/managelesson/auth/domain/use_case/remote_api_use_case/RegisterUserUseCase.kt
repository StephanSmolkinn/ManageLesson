package com.project.managelesson.auth.domain.use_case.remote_api_use_case

import com.project.managelesson.auth.data.data_source.remote.ApiState
import com.project.managelesson.auth.data.data_source.remote.RemoteError
import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.auth.domain.parameters.RegisterUserParameters
import com.project.managelesson.auth.domain.repository.UserRemoteRepository
import com.project.managelesson.core.domain.util.errorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUserUseCase(
    private val userRemoteRepository: UserRemoteRepository
) {
    operator fun invoke(registerUser: RegisterUserParameters): Flow<ApiState<User, RemoteError.DataError>> = flow {
        val response = userRemoteRepository.registerUser(registerUser)

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
                println(it)
            }
        } else {
            response.errorBody()?.let {
                emit(errorResponse(response.code()))
            }
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(ApiState.Failure(RemoteError.DataError.UNKNOWN))
            println(Thread.currentThread().name)
        }.flowOn(Dispatchers.IO)
}