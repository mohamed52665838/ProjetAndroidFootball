package com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.Activities
import com.example.projetandroid.Home
import com.example.projetandroid.R
import com.example.projetandroid.Settings
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.DashboardComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.DashboardState
import com.example.projetandroid.ui_layer.ui.theme.secondaryColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel

@Composable
fun DashboardScaffold(
    androidNavController: NavHostController,
    viewModel: DashboardViewModel,
) {
    var currentFragment by rememberSaveable {
        mutableStateOf(DashboardState.HOME_FRAGMENT)
    }

    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = secondaryColor,
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
                        indicatorColor = secondaryColor,
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
                        indicatorColor = secondaryColor,
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
                    })
            }
        }

    ) {
        AnimatedContent(
            targetState = currentFragment, Modifier.padding(it),
            label = "dashboard scaffolding"
        ) { currentFragment ->
            when (currentFragment) {
                DashboardState.HOME_FRAGMENT ->
                    DashboardComposable(
                        navController = androidNavController,
                        viewModel = viewModel
                    )

                DashboardState.ACTIVITY_FRAGMENT ->
                    ActivityComposable(
                        dashboardViewModel = viewModel,
                        androidNavController = androidNavController
                    )

                DashboardState.PROFILE_FRAGMENT ->
                    SettingsComposable(
                        navController = androidNavController,
                        dashboardViewModel = viewModel,
                        onSignOut = { navHostController.popBackStack() })
            }
        }
    }
}
