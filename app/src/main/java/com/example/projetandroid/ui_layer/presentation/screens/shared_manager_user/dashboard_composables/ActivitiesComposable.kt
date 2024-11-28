package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projetandroid.AddMatch
import com.example.projetandroid.MatchScreen
import com.example.projetandroid.R
import com.example.projetandroid.Role
import com.example.projetandroid.UiState
import com.example.projetandroid.translateRole
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.Match
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchCard
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchesUI
import com.example.projetandroid.ui_layer.presentation.shared_components.OwnMatch
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityComposable(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModelBase = hiltViewModel(),
    matchesViewModel: MatchesViewModel,
    androidNavController: NavController,
) {
    val activityComposableChannel = dashboardViewModel.uiState.collectAsState(UiState.Idle)



    Scaffold(
        floatingActionButton = {
            if (dashboardViewModel.user.value?.role == translateRole(Role.USER))
                FloatingActionButton(onClick = {
                    androidNavController.navigate(AddMatch)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "add"
                    )
                }
        },
        topBar = {
            TopAppBar(title = {
                Text(text = "Activities", style = MaterialTheme.typography.titleLarge)
            }
            )
        }

    ) {
        Box(
            modifier = modifier
                .padding(it)
        ) {
            HandleUIEvents(
                uiState = activityComposableChannel.value,
                modifier = Modifier.zIndex(1f),
                navController = androidNavController
            )

            matchesViewModel.matches_.value?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    items(it) {
                        MatchCard(
                            match = OwnMatch(
                                labelOfTerrain = it.terrainId.label,
                                date = it.date,
                                it.playersOfMatch.size,
                                it.terrainId.price,
                            ),
                            onClick = {
                                androidNavController.navigate(MatchScreen(it._id))
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

            }

        }

    }
}


