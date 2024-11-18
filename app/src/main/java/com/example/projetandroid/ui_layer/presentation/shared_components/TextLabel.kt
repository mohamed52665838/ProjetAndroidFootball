package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun TextLabel(text: String,modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.titleSmall.copy(color = Color.Green.copy(alpha = 0.7f)), modifier = modifier)
}