package com.project.managelesson.domain.use_case.remote_api_use_case

import com.project.managelesson.data.data_source.remote.ApiState
import com.project.managelesson.data.data_source.remote.LoginUserParameters
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import com.project.managelesson.domain.repository.UserRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUserUseCase(
    private val userRemoteRepository: UserRemoteRepository
) {
    suspend operator fun invoke(loginUserParameters: LoginUserParameters): Flow<ApiState<UserResponse>> =
        flow {
            val response = userRemoteRepository.loginUser(loginUserParameters)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                    println(it)
                    println(Thread.currentThread().name)
                }
            } else {
                response.errorBody()?.let {
                    emit(ApiState.Failure("Invalid user"))
                }
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                emit(ApiState.Failure("User not found"))
            }.flowOn(Dispatchers.IO)
}