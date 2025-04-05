package com.project.managelesson.auth.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.managelesson.study.domain.model.Subject

@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    subject: Subject,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(
                brush = Brush.verticalGradient(colors),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = subject.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier,
                    text = "Select day",
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}