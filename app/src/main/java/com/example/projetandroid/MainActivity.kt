package com.example.projetandroid

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.projetandroid.model.User
import com.example.projetandroid.ui_layer.presentation.manager.AddSoccerFieldComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.DashboardComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.DashboardState
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.LoginComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.OTAValidatorComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.ProfileComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.SettingsComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.SignupComposable
import com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables.DashboardScaffold
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.secondaryColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.TypeVariable


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
                                androidNavController,
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
                            SignupComposable(androidNavController)
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
                            OTAValidatorComposable(
                                navController = androidNavController,
                                email = email
                            )
                        }
                        navigation<Dashboard>(startDestination = DashboardScaffold) {
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
                            composable<Profile> {
                                val parentEntry = remember(it) {
                                    androidNavController.getBackStackEntry(Dashboard)
                                }
                                val viewModel: DashboardViewModel = hiltViewModel(parentEntry)
                                ProfileComposable(
                                    viewModel = viewModel,
                                    navController = androidNavController
                                )
                            }
                            composable<AddSoccerField> {
                                AddSoccerFieldComposable(
                                    viewModel = hiltViewModel(),
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


