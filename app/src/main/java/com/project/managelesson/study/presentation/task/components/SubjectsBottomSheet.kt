package com.project.managelesson.study.presentation.task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.managelesson.study.domain.model.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsBottomSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    subjectList: List<Subject>,
    title: String = "Relate to subject",
    onClickDismiss: () -> Unit,
    onClickSubject: (Subject) -> Unit,
) {
    if (isOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onClickDismiss,
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = title)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(subjectList) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClickSubject(it) }
                            .padding(8.dp)
                    ) {
                        Text(text = it.title)
                    }
                }
                if (subjectList.isEmpty()) {
                    item {
                        Text(text = "You do not have any subjects")
                    }
                }
            }
        }
    }
}