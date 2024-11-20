package com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityComposable(
    dashboardViewModel: DashboardViewModel,
    androidNavController: NavController,
    modifier: Modifier = Modifier
) {
    val activityComposableChannel = dashboardViewModel.activityUiState.collectAsState(UiState.Idle)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Activities", style = MaterialTheme.typography.titleLarge)
            }
            )
        }

    ) {
        Box(modifier = modifier
            .padding(it)
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Coming Soon")
            when (activityComposableChannel.value) {
                is UiState.Error -> {
                    AlertDialog(onDismissRequest = { dashboardViewModel.activityRestore() },
                        confirmButton = {
                            TextButton(onClick = {
                                dashboardViewModel.activityRestore()
                            }) {
                                Text(text = "OK")
                            }
                        },
                        text = {
                            Text(text = (activityComposableChannel.value as UiState.Error).message)
                        }
                    )
                }

                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {

                    AlertDialog(onDismissRequest = { dashboardViewModel.activityRestore() },
                        confirmButton = {
                            TextButton(onClick = {
                                dashboardViewModel.activityRestore()
                            }) {
                                Text(text = "OK")
                            }
                        },
                        text = {
                            Text(text = (activityComposableChannel.value as UiState.Success).message)
                        }
                    )

                }

                is UiState.Idle -> {
                    // empty
                }
            }

        }
    }
}


