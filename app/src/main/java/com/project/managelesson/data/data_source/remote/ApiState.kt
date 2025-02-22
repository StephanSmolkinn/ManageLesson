package com.project.managelesson.data.data_source.remote

sealed class ApiState<out T> {
    data class Success<out T> (val data: T) : ApiState<T>()
    data class Failure(val message: String) : ApiState<Nothing>()
}