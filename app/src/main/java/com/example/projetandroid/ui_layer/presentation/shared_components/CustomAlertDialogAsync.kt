package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

data class AlertDialogComponent(
    val onConfirmAction: (() -> Unit)? = null,
    val onDismissAction: (() -> Unit)? = null,
    var isError: Boolean = false,
    var content: @Composable () -> Unit,
)

data class AlertDialogAsyncState(
    val stateOfDialog: MutableState<Boolean> = mutableStateOf(false),
    var alertDialogComponent: AlertDialogComponent? = null
) {
    val alertDialogComponentMaintained: AlertDialogComponent? = alertDialogComponent
}


fun AlertDialogAsyncState.show(alertDialogComponent: AlertDialogComponent? = null) {
    alertDialogComponent?.let {
        this.alertDialogComponent = it
    }
    this.stateOfDialog.value = true
}


fun AlertDialogAsyncState.hide() {
    this.stateOfDialog.value = false
    this.restoreState()
}

private fun AlertDialogAsyncState.restoreState() {
    this.alertDialogComponent = alertDialogComponentMaintained
}

@Composable
fun rememberAlertDialogAsyncState(alertDialogComponent: AlertDialogComponent? = null): AlertDialogAsyncState {
    return remember {
        AlertDialogAsyncState(alertDialogComponent = alertDialogComponent)
    }
}


@Composable
fun AlertDialogAsyncComponent(alertDialogAsyncState: AlertDialogAsyncState = rememberAlertDialogAsyncState()) {
    if (alertDialogAsyncState.stateOfDialog.value) {
        alertDialogAsyncState.alertDialogComponent?.let {
            AlertDialog(
                containerColor = if (it.isError) Color.Red.copy(alpha = 0.4f) else AlertDialogDefaults.containerColor,
                onDismissRequest = {
                    alertDialogAsyncState.stateOfDialog.value = false
                }, confirmButton = {
                    it.onConfirmAction?.let {
                        TextButton(onClick = {
                            it()
                            alertDialogAsyncState.hide()
                        }) {
                            Text(text = "confirm", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                },
                dismissButton = {
                    it.onDismissAction?.let {
                        TextButton(onClick = {
                            it()
                            alertDialogAsyncState.stateOfDialog.value = false
                        }) {
                            Text(text = "cancel", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                },
                text = {
                    it.content()
                }
            )
        } ?: run {
            AlertDialog(onDismissRequest = {
                alertDialogAsyncState.stateOfDialog.value = false
            }, confirmButton = {
                TextButton(onClick = {
                    alertDialogAsyncState.stateOfDialog.value = false
                }) {
                    Text(text = "OK", style = MaterialTheme.typography.titleMedium)
                }
            })
        }
    }
}