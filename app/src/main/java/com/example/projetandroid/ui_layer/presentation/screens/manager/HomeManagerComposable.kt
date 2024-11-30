package com.example.projetandroid.ui_layer.presentation.screens.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.navigation.NavController
import com.example.projetandroid.AddSoccerField
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.model.manager.Statistics
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.HomeManagerViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeManagerComposable(
    dashboardViewMode: DashboardViewModelBase,
    homeManagerViewModelBase: HomeManagerViewModel,
    navController: NavController
) {


    val motherFuckerMatch = homeManagerViewModelBase.soccerFieldWatcher.value
    val user = dashboardViewMode.user.value
    val isDataLoaded = homeManagerViewModelBase.isLoadedData.value


    HandleUIEvents(
        uiState =
        homeManagerViewModelBase.uiStateWatcher.collectAsState(initial = UiState.Idle).value,
        navController = navController,
    )


    LaunchedEffect(key1 = Unit) {
        navController.previousBackStackEntry?.savedStateHandle?.getLiveData<String>("201")
            ?.observeForever {
                if (it == "OK") {
                    homeManagerViewModelBase.loadSoccerField()
                }
            }


    }
    user?.let {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            Text(
                                text = it.name.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "'s ",
                                style = MaterialTheme.typography.titleLarge,
                                color = tertiaryColor
                            )
                            Text(
                                text = "board",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_settings_24),
                                contentDescription = "settings"
                            )
                        }
                    }
                })
            }

        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                if (isDataLoaded) {
                    motherFuckerMatch?.let {
                        Column {
                            Text(
                                text = "Welcome ${user.name.capitalize(Locale.ROOT)}",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("d MMM yyyy")),
                                style = MaterialTheme.typography.titleMedium
                            )
                            StatisticsView(
                                statistics = Statistics(
                                    (it.price?.times(it.matchsIn?.size!!)),
                                    countMatchs = it.matchsIn?.size ?: 0
                                )
                            )
                        }
                    } ?: kotlin.run {

                        Column {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                TextButton(onClick = {
                                    navController.navigate(AddSoccerField)
                                }) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            "Add Soccer Field",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_add_24),
                                            contentDescription = "content"
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticsView(statistics: Statistics) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
        OutlinedCard(
            modifier = Modifier.weight(1f),
            onClick = {

            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "${statistics.countMatchs} Plays",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        OutlinedCard(
            modifier = Modifier.weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_price_check_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "${statistics.amout} dt",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


@Composable
fun TodaysPlayCount(numberOfPlayes: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
        OutlinedCard(
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Today's $numberOfPlayes plays",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
