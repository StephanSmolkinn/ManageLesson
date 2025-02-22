package com.project.managelesson.domain.use_case.remote_api_use_case

import com.project.managelesson.data.data_source.remote.ApiState
import com.project.managelesson.data.data_source.remote.responses.UserResponse
import com.project.managelesson.domain.repository.UserRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUserUseCase(
    private val userRemoteRepository: UserRemoteRepository
) {
    suspend operator fun invoke(userResponse: UserResponse): Flow<ApiState<UserResponse>> = flow {
        val response = userRemoteRepository.registerUser(userResponse)

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
                println(it)
            }
        } else {
            response.errorBody()?.let {
                emit(ApiState.Failure("Error"))
            }
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(ApiState.Failure("Register failure"))
            println(Thread.currentThread().name)
        }.flowOn(Dispatchers.IO)
}