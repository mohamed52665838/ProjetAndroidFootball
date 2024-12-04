package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.Activities
import com.example.projetandroid.DashboardScaffold
import com.example.projetandroid.Home
import com.example.projetandroid.R
import com.example.projetandroid.Role
import com.example.projetandroid.Settings
import com.example.projetandroid.UiState
import com.example.projetandroid.translateRole
import com.example.projetandroid.ui_layer.presentation.screens.manager.HomeManagerComposable
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.screens.user.HomeUserComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables.DashboardComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables.DashboardState
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.secondaryColor
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.HomeManagerViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelPreview
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchesViewModel
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.HomeUserViewModel

@Composable
fun DashboardScaffold(
    androidNavController: NavHostController,
    viewModel: DashboardViewModelBase = DashboardViewModelPreview(),
) {
    var currentFragment by rememberSaveable {
        mutableStateOf(DashboardState.HOME_FRAGMENT)
    }

    HandleUIEvents(
        uiState = viewModel.uiState.collectAsState(initial = UiState.Idle).value,
        navController = androidNavController,
        onDone = { viewModel.restUiState() })
    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.padding(0.dp)) {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = tertiaryColor,
                        selectedIconColor = Color.White
                    ),
                    selected = currentFragment == DashboardState.HOME_FRAGMENT,
                    onClick = {
                        currentFragment = DashboardState.HOME_FRAGMENT
                    },

                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_home_filled_24),
                            contentDescription = "home"
                        )
                    })

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = tertiaryColor,
                        selectedIconColor = Color.White
                    ),
                    selected = currentFragment == DashboardState.ACTIVITY_FRAGMENT,
                    onClick = {
                        currentFragment = DashboardState.ACTIVITY_FRAGMENT
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                            contentDescription = "home"
                        )
                    })
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = tertiaryColor,
                        selectedIconColor = Color.White
                    ),
                    selected = currentFragment == DashboardState.PROFILE_FRAGMENT,
                    onClick = {
                        currentFragment = DashboardState.PROFILE_FRAGMENT
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = "home"
                        )
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding()
            )
        ) {
            viewModel.user.value?.let {
                AnimatedContent(
                    targetState = currentFragment,
                    label = "dashboard scaffolding"
                ) { currentFragment ->
                    when (currentFragment) {
                        DashboardState.HOME_FRAGMENT -> {
                            if (it.role == translateRole(Role.MANAGER))
                                HomeManagerComposable(
                                    navController = androidNavController,
                                    dashboardViewMode = viewModel,
                                    homeManagerViewModelBase = hiltViewModel<HomeManagerViewModel>()
                                )
                            else {
                                HomeUserComposable(
                                    dashboardViewModel = viewModel,
                                    navController = androidNavController
                                )
                            }
                        }

                        DashboardState.ACTIVITY_FRAGMENT -> {
                            ActivityComposable(
                                dashboardViewModel = viewModel,
                                androidNavController = androidNavController,
                                matchesViewModel = hiltViewModel<MatchesViewModel>()
                            )
                        }

                        DashboardState.PROFILE_FRAGMENT ->
                            SettingsComposable(
                                navController = androidNavController,
                                dashboardViewModel = viewModel,
                                onSignOut = { androidNavController.popBackStack() })
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DashboardScaffoldPreview() {
    ProjetAndroidTheme {
        DashboardScaffold(rememberNavController(), viewModel = DashboardViewModelPreview())
    }
}
