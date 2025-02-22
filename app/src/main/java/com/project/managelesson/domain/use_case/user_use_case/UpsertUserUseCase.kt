package com.project.managelesson.domain.use_case.user_use_case

import com.project.managelesson.domain.model.User
import com.project.managelesson.domain.repository.UserRepository

class UpsertUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.upsertUser(user)
    }
}