package com.project.managelesson.core.domain.use_cases.user_use_case

import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.core.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? = userRepository.getUser()
}