package com.example.projetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.projetandroid.ui_layer.presentation.screens.manager.AddSoccerFieldComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.LoginComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.MapOfMatch
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.MatchComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.OTAValidatorComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.ProfileComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.SignupComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables.DashboardScaffold
import com.example.projetandroid.ui_layer.presentation.screens.user.AddNowMatchComposable
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.LoginViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.OTACodeViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.ProfileViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val androidNavController = rememberNavController()
            val currentBackStack by androidNavController.currentBackStackEntryAsState()
            val destination = currentBackStack?.destination

            ProjetAndroidTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    topBar = {
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = androidNavController,
                        startDestination = SignIn
                    ) {

                        composable<SignIn>(
                            enterTransition = {
                                scaleIn()
                            },
                            exitTransition = {
                                scaleOut()
                            }
                        ) {
                            LoginComposable(
                                viewModel = hiltViewModel<LoginViewModel>(),
                                navController = androidNavController,
                            )
                        }
                        composable<SignUp>(
                            enterTransition = {
                                scaleIn()
                            },
                            exitTransition = {
                                scaleOut()
                            }
                        ) {
                            SignupComposable(
                                androidNavController,
                                viewModel = hiltViewModel<SignupViewModel>()
                            )
                        }

                        composable<CodeOTP>(
                            enterTransition = {
                                scaleIn()
                            },

                            exitTransition = {
                                scaleOut()
                            }
                        ) {
                            val email = it.toRoute<CodeOTP>().email
                            val viewModel: OTACodeViewModel =
                                hiltViewModel<OTACodeViewModel, OTACodeViewModel.OTACodeViewModelFactory>(
                                    creationCallback = { otaCodeViewModelFactory ->
                                        otaCodeViewModelFactory.create(
                                            email
                                        )
                                    }
                                )
                            OTAValidatorComposable(
                                viewModel = viewModel,
                                navController = androidNavController,
                            )
                        }
                        navigation<Dashboard>(startDestination = DashboardScaffold) {
                            composable<AddMatch> {
                                AddNowMatchComposable(
                                    addNowMatchViewModel = hiltViewModel(),
                                    navController = androidNavController
                                )
                            }

                            // root dashboard
                            composable<DashboardScaffold> {
                                val parentEntry = remember(it) {
                                    androidNavController.getBackStackEntry(Dashboard)
                                }
                                val viewModel: DashboardViewModel = hiltViewModel(parentEntry)
                                DashboardScaffold(
                                    viewModel = viewModel,
                                    androidNavController = androidNavController,
                                )
                            }


                            // profile composable function
                            composable<Profile> {
                                val parentEntry = remember(it) {
                                    androidNavController.getBackStackEntry(Dashboard)
                                }
                                val viewModel: DashboardViewModel = hiltViewModel(parentEntry)
                                val profileViewModel =
                                    hiltViewModel<ProfileViewModel, ProfileViewModel.ProfileViewModelAssistant>(
                                        creationCallback = { factory ->
                                            factory.create(viewModel.user.value!!)
                                        })
                                ProfileComposable(
                                    viewModel = viewModel,
                                    profileViewModel = profileViewModel,
                                    navController = androidNavController
                                )
                            }

                            // addSoccerField viewModel
                            composable<AddSoccerField> {
                                AddSoccerFieldComposable(
                                    viewModel = hiltViewModel(),
                                    navController = androidNavController
                                )
                            }
                            composable<MatchScreen> {
                                val parentEntry = remember(it) {
                                    androidNavController.getBackStackEntry(Dashboard)
                                }

                                val viewModel: DashboardViewModel = hiltViewModel(parentEntry)
                                val matchId = currentBackStack!!.toRoute<MatchScreen>()
                                val matchViewModel =
                                    hiltViewModel<MatchViewModel, MatchViewModel.MatchViewModelFactory>(
                                        creationCallback = { factory ->
                                            factory.create(matchId.matchId ?: "noway")
                                        })
                                MatchComposable(
                                    dashboardViewModel = viewModel,
                                    matchViewModel = matchViewModel,
                                    navController = androidNavController
                                )
                            }

                            composable<MapScreen> {
                                val matchId = currentBackStack!!.toRoute<MapScreen>()
                                val init_ = 0
                                MapOfMatch(
                                    latitude = matchId.lat ?: init_.toDouble(),
                                    long = matchId.lon ?: init_.toDouble(),
                                    navController = androidNavController
                                )
                            }

                        }

                    }
                }
            }
        }
    }
}


// up
// username / password submit
// receive otp code
// otp code && send with email
// login
// dashboard


// row / column
@kotlinx.serialization.Serializable
object Sign

@kotlinx.serialization.Serializable
object GeneralSign

// row / column
@kotlinx.serialization.Serializable
object SignIn

@kotlinx.serialization.Serializable
object SignUp

@kotlinx.serialization.Serializable
object Dashboard

@kotlinx.serialization.Serializable
class CodeOTP(val email: String)


// dashboard related routes

@kotlinx.serialization.Serializable
object Profile


@kotlinx.serialization.Serializable
object DashboardScaffold


// dashboard scaffold route
@kotlinx.serialization.Serializable
object Home

@kotlinx.serialization.Serializable
object Settings

@kotlinx.serialization.Serializable
object Activities

@kotlinx.serialization.Serializable
object AddSoccerField

@kotlinx.serialization.Serializable
object SplashScreen


@kotlinx.serialization.Serializable
data class MatchScreen(
    val matchId: String? = null
)


@kotlinx.serialization.Serializable
data class MapScreen(
    val lat: Double? = null,
    val lon: Double? = null,
)


@kotlinx.serialization.Serializable
data object AddMatch

