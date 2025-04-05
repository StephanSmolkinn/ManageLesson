package com.project.managelesson.auth.data.mappers

import com.project.managelesson.core.data.data_model.UserEntity
import com.project.managelesson.auth.domain.model.User

fun UserEntity.toDomainUser(): User {
    return User(
        id = id,
        fullName = fullName,
        email = email,
        authToken = authToken
    )
}

fun User.toDataUser(): UserEntity {
    return UserEntity(
        id = id,
        fullName = fullName,
        email = email,
        authToken = authToken
    )
}