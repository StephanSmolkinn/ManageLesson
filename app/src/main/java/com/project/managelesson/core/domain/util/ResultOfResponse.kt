package com.project.managelesson.core.domain.util

import com.project.managelesson.auth.data.data_source.remote.ApiState
import com.project.managelesson.auth.data.data_source.remote.RemoteError

suspend inline fun errorResponse(
    statusCode: Int
): ApiState.Failure<RemoteError.DataError> {
    return when(statusCode) {
        408 -> {
            ApiState.Failure(RemoteError.DataError.REQUEST_TIMEOUT)
        }
        429 -> {
            ApiState.Failure(RemoteError.DataError.TOO_MANY_REQUESTS)
        }
        in 500..599 -> {
            ApiState.Failure(RemoteError.DataError.SERVER)
        }

        else -> {
            ApiState.Failure(RemoteError.DataError.UNKNOWN)
        }
    }
}