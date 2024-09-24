package com.project.managelesson.presentation.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.managelesson.R
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.presentation.dashboard.components.EmptyListSection
import com.project.managelesson.presentation.dashboard.components.LessonCard

fun LazyListScope.lessonList(
    title: String,
    lessonList: List<Lesson>,
    text: String,
    onClickDelete: (Lesson) -> Unit
) {
    item {
        Row {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    if (lessonList.isEmpty()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmptyListSection(
                    modifier = Modifier
                        .size(120.dp),
                    text = text,
                    image = R.drawable.lamp
                )
            }
        }
    }
    items(lessonList) {
        LessonCard(
            lesson = it,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp),
            onClickDelete = { onClickDelete(it) }
        )
    }
}