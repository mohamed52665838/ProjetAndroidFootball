package com.example.projetandroid.ui_layer.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents

@Composable
fun SupportUiStatusBox(
    modifier: Modifier = Modifier,
    uiStatus: State<UiState>,
    controller: NavController,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        HandleUIEvents(
            uiState = uiStatus.value,
            navController = controller,
            modifier = Modifier
                .matchParentSize()
                .zIndex(999f)
        )
        content.invoke() // keep it like that :)
    }
}