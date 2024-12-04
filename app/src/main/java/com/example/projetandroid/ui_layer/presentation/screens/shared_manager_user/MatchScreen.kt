package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projetandroid.AddMatch
import com.example.projetandroid.Dashboard
import com.example.projetandroid.DashboardScaffold
import com.example.projetandroid.MapScreen
import com.example.projetandroid.R
import com.example.projetandroid.Role
import com.example.projetandroid.UiState
import com.example.projetandroid.model.match.matchModel.PlayersOfMatch
import com.example.projetandroid.model.match.matchModel.UserId
import com.example.projetandroid.translateRole
import com.example.projetandroid.ui_layer.presentation.SupportUiStatusBox
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.PlayerOfMatchUiModel
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchComposable(
    dashboardViewModel: DashboardViewModel,
    matchViewModel: MatchViewModel,
    navController: NavController
) {

    val currentMatch = matchViewModel.matchReponseModel.value
    val uiState_ = matchViewModel.uiState.collectAsState(initial = UiState.Idle)


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Match")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "back previous screen"
                        )
                    }
                },
                actions = {
                    matchViewModel.matchReponseModel.value?.let {
                        IconButton(onClick = {
                            navController.navigate(
                                MapScreen(
                                    it.terrainId.latitude.toDouble(),
                                    it.terrainId.longitude.toDouble()
                                )
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_location_on_24),
                                contentDescription = "location match"
                            )
                        }
                    }
                }
            )
        }
    ) {
        SupportUiStatusBox(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            controller = navController,
            uiStatus = uiState_
        ) {
            currentMatch?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(_root_ide_package_.androidx.compose.foundation.rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (dashboardViewModel.user.value!!.role == translateRole(Role.USER)
                        && dashboardViewModel.user.value!!.id != it.userId._id &&
                        !matchViewModel.playersOfMatch.any { it.userId._id == dashboardViewModel.user.value!!.id }
                    ) {
                        // row for users only
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Status", style = MaterialTheme.typography.titleLarge)
                            TextButton(onClick = { matchViewModel.joinMatch(it._id) }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_add_24),
                                        contentDescription = "simple string"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "join request",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    } // 674f95792f8ac64f1cca1341
                    Text(
                        text = "ID: ${it._id}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Date", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = it.date.split("T")[0],
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Time", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = it.date.split("T")[1],
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Price", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = it.terrainId.price.toString(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Members",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = it.playersOfMatch.size.toString(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // the list of players right here
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Members", style = MaterialTheme.typography.titleLarge)
                    }

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Creator",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    PlayerOfMatchUiModel(
                        playersOfMatch = PlayersOfMatch(
                            _id = "", // no need to match id the created already joined
                            matchId = it._id,
                            userId = UserId(
                                name = it.userId.name,
                                _id = it.userId.email ?: it.userId._id
                            ),
                            isAccepted = true

                        ), isShowSuggestions = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Members",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    if (matchViewModel.playersOfMatch.isEmpty())
                        Box(
                            modifier = Modifier
                                .zIndex(10f),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = "No Members for Now",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Gray
                            )
                        }
                    else
                        matchViewModel.playersOfMatch.map { matchPlayer ->
                            PlayerOfMatchUiModel(
                                playersOfMatch = matchPlayer,
                                isShowSuggestions = it.userId._id == dashboardViewModel.user.value!!.id,
                                onAccept = {
                                    matchViewModel.accpetUser(matchPlayer._id)
                                },
                                onRefuse = {
                                    matchViewModel.refuseUser(matchPlayer._id)
                                }

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }


                }
            }
        }
    }
}

data class Person(var name: String)

@Preview(showSystemUi = true)
@Composable
private fun MatchScreenViewModel() {

    val list_ = remember {
        mutableStateListOf(
            Person(name = "a"),
            Person(name = "b"),
            Person(name = "c"),
        )
    }

    ProjetAndroidTheme {
        Column {
            Button(onClick = {
                val person = list_[0]
                val personCopy = person.copy(name = "hello")
                list_.remove(person)
                list_.add(personCopy)
            }) {
                Text(text = "change state")
            }
            list_.map {
                Text(text = it.name)
            }
        }
    }
}