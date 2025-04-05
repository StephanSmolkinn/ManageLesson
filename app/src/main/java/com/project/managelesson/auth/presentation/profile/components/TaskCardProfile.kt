package com.project.managelesson.auth.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskCardProfile(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    priority: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when(priority) {
                    0 -> Color.Green
                    1 -> Color.Yellow
                    2 -> Color.Red

                    else -> Color.Transparent
                },
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .size(width = 80.dp, height = 20.dp)
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.DarkGray
                )
            }
        }
    }
}