package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialUseStateExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier.
        background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(16f))
    ) {
        TimePicker(
            state = timePickerState,
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = onDismiss) {
                Text("cancel", style = MaterialTheme.typography.titleMedium)
            }
            TextButton(onClick = { onConfirm(timePickerState) }) {
                Text("confirm", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun DialogPreview() {
    DialUseStateExample(onConfirm = {}) {
    }
}