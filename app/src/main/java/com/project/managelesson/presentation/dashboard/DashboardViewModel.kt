package com.project.managelesson.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.project.managelesson.domain.use_case.ManageLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val manageLessonUseCase: ManageLessonUseCase
) : ViewModel() {

}