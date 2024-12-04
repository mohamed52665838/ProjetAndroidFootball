package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.projetandroid.ui_layer.event.EventsBus
import com.example.projetandroid.ui_layer.presentation.SupportUiStatusBox
import com.example.projetandroid.ui_layer.presentation.shared_components.AlertDialogAsyncComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.AlertDialogAsyncState
import com.example.projetandroid.ui_layer.presentation.shared_components.AlertDialogComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.Match
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchCard
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchesUI
import com.example.projetandroid.ui_layer.presentation.shared_components.OwnMatch
import com.example.projetandroid.ui_layer.presentation.shared_components.hide
import com.example.projetandroid.ui_layer.presentation.shared_components.rememberAlertDialogAsyncState
import com.example.projetandroid.ui_layer.presentation.shared_components.show
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchesViewModel
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityComposable(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModelBase = hiltViewModel(),
    matchesViewModel: MatchesViewModel,
    androidNavController: NavController,
) {
    val activityComposableChannel = matchesViewModel.uiState.collectAsState(UiState.Idle)
    val scrollState = rememberLazyListState()
    val shadowDp = animateDpAsState(
        targetValue = if (scrollState.isScrollInProgress || scrollState.canScrollBackward) 8.dp else 0.dp,
        label = "shadow of top appbar"
    )

    val alertDialogState = rememberAlertDialogAsyncState()
    AlertDialogAsyncComponent(alertDialogState)

    Scaffold(
        floatingActionButton = {
            if (dashboardViewModel.isUser())
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
            Surface(
                shadowElevation = shadowDp.value
            ) {
                TopAppBar(title = {
                    Text(text = "Activities", style = MaterialTheme.typography.titleLarge)
                },
                    actions = if (dashboardViewModel.isUser()) {
                        {
                            IconButton(onClick = {
                                alertDialogState.show(
                                    AlertDialogComponent {
                                        val valueOfCode = remember {
                                            mutableStateOf("")
                                        }
                                        var isError by remember {
                                            mutableStateOf(false)
                                        }
                                        Column {
                                            Text(
                                                text = "Join with id",
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            OutlinedTextField(
                                                isError = isError,
                                                value = valueOfCode.value,
                                                onValueChange = { valueOfCode.value = it },
                                                supportingText = {
                                                    Text(text = "match id 24 character")
                                                },
                                                label = {
                                                    Text(text = "code")
                                                }
                                            )

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                TextButton(onClick = {
                                                    alertDialogState.hide()
                                                }) {
                                                    Text(
                                                        text = "cancel",
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                }
                                                TextButton(onClick = {
                                                    if (valueOfCode.value.length != 24) {
                                                        isError = true
                                                    } else {
                                                        matchesViewModel.joinMatch(valueOfCode.value)
                                                        alertDialogState.hide()
                                                    }
                                                    println("confirmed")
                                                }) {
                                                    Text(
                                                        text = "confirm",
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                }
                                            }

                                        }

                                    }
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_contactless_24),
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        {}
                    }
                )
            }
        }
    ) {
        SupportUiStatusBox(
            modifier = modifier
                .padding(top = it.calculateTopPadding()),
            controller = androidNavController,
            uiStatus = activityComposableChannel
        ) {

            matchesViewModel.matches_.let {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    items(it, key = { it._id }) {
                        MatchCard(
                            modifier = Modifier,
                            match = OwnMatch(
                                labelOfTerrain = it.terrainId.label,
                                date = it.date,
                                it.playersOfMatch.size,
                                it.terrainId.price,
                                isMine = it.userId._id == dashboardViewModel.user.value?.id
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


@Preview(showSystemUi = true)
@Composable
private fun AnimationReplacement() {
    var list by remember { mutableStateOf(listOf("A", "B", "C")) }
    LazyColumn {
        item {
            Button(onClick = { list = list.shuffled() }) {
                Text("Shuffle")
            }
        }
        items(list, key = { it }) {
            Text("Item $it", Modifier.animateItem())
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun AlertPreview() {
    val state = rememberAlertDialogAsyncState(
        AlertDialogComponent(
            onDismissAction = {
                println("Hello World Dismiss")
            },
            onConfirmAction = {
                println("Hello, world! confirm")
            },
            content = {
                Column {
                    Text(text = "Simple Text", style = MaterialTheme.typography.titleMedium)
                }
            },
        )
    )
    Button(onClick = {
        state.show()
    }) {
        Text(text = "show dialog")
    }
    ProjetAndroidTheme {

        AlertDialogAsyncComponent(state)
    }

}