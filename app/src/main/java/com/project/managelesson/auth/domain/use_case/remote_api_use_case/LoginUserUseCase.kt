package com.project.managelesson.auth.domain.use_case.remote_api_use_case

import com.project.managelesson.auth.data.data_source.remote.ApiState
import com.project.managelesson.auth.data.data_source.remote.RemoteError
import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.auth.domain.parameters.LoginUserParameters
import com.project.managelesson.auth.domain.repository.UserRemoteRepository
import com.project.managelesson.core.domain.util.errorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUserUseCase(
    private val userRemoteRepository: UserRemoteRepository
) {
    operator fun invoke(loginUserParameters: LoginUserParameters): Flow<ApiState<User, RemoteError.DataError>> =
        flow {
            val response = userRemoteRepository.loginUser(loginUserParameters)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                }
            } else {
                response.errorBody()?.let {
                    emit(errorResponse(response.code()))
                }
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                emit(ApiState.Failure(RemoteError.DataError.SERVER))
            }.flowOn(Dispatchers.IO)
}