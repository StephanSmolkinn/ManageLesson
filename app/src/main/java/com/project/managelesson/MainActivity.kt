package com.project.managelesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.managelesson.presentation.dashboard.DashboardScreen
import com.project.managelesson.presentation.theme.ManageLessonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageLessonTheme {
                DashboardScreen()
            }
        }
    }
}
