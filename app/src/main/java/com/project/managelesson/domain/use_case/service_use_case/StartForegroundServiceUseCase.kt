package com.project.managelesson.domain.use_case.service_use_case

class StartForegroundServiceUseCase {
    operator fun invoke() {
        CreateNotificationChannelUseCase()
    }
}