package com.project.managelesson.auth.data.data_source.remote

import com.project.managelesson.core.domain.util.Error

sealed interface RemoteError : Error {
    enum class DataError : RemoteError {
        BAD_REQUEST,
        TOO_MANY_REQUESTS,
        REQUEST_TIMEOUT,
        SERVER,
        USER_NOT_FOUND,
        UNKNOWN
    }
}