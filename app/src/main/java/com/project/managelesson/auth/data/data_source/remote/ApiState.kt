package com.project.managelesson.auth.data.data_source.remote

import com.project.managelesson.core.domain.util.Error

sealed class ApiState<out T, out E: Error> {
    data class Success<out T> (val data: T) : ApiState<T, Nothing>()
    data class Failure<out E: Error>(val error: E) : ApiState<Nothing, E>()
}