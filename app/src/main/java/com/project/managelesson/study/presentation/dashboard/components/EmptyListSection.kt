package com.project.managelesson.study.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListSection(
    modifier: Modifier = Modifier,
    text: String,
    image: Int
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = image),
        contentDescription = null
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        color = Color.Gray
    )
}