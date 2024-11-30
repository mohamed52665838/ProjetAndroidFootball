package com.example.projetandroid.ui_layer.presentation.screens.user

import android.app.Activity
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.MatchScreen
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.presentation.shared_components.Match
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchCard
import com.example.projetandroid.ui_layer.presentation.shared_components.MatchJointedCard
import com.example.projetandroid.ui_layer.presentation.shared_components.OwnMatch
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelPreview
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.HomeUserViewModel
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.HomeUserViewModelBase
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.HomeUserViewModelPreview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserComposable(
    dashboardViewModel: DashboardViewModelBase,
    navController: NavController,
    homeUserViewModelBase: HomeUserViewModelBase = hiltViewModel<HomeUserViewModel>()
) {

    var currentTab by remember {
        mutableIntStateOf(0)
    }

    val user = dashboardViewModel.user.value
    val listOfJointedMatches = homeUserViewModelBase.jointedMatchX
    val listOfOwnmatches = homeUserViewModelBase.myOwnMatches

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
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues)
            ) {
                Column {
                    Text(text = "My Own Matches", style = MaterialTheme.typography.titleMedium)
                    TabRow(selectedTabIndex = currentTab) {
                        Tab(selected = currentTab == 0, onClick = {
                            currentTab = 0
                        }) {
                            Text(text = "Own matches")
                        }
                        Tab(selected = currentTab == 1, onClick = { currentTab = 1 }) {
                            Text(text = "Jointed")
                        }
                    }

                    when (currentTab) {
                        0 -> {

                            LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                                items(listOfOwnmatches) {
                                    MatchCard(
                                        match = OwnMatch(
                                            it.terrainId.label,
                                            it.date,
                                            it.playersOfMatch.size,
                                            it.terrainId.price.toDouble()
                                        ),
                                        onClick = { navController.navigate(MatchScreen(it._id)) }
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        1 -> {
                            LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                                items(listOfJointedMatches) {
                                    MatchJointedCard(
                                        match = Match(
                                            it.isAccepted,
                                            it.matchId.date,
                                            it.matchId.playersOfMatch.size
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeUserComposablePreview() {
    ProjetAndroidTheme {
//        HomeUserComposable(
//            dashboardViewModel = DashboardViewModelPreview(),
//            homeUserViewModelBase = HomeUserViewModelPreview()
//        )
    }
}