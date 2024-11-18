package com.example.projetandroid

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.projetandroid.ui_layer.presentation.DashboardComposable
import com.example.projetandroid.ui_layer.presentation.LoginComposable
import com.example.projetandroid.ui_layer.presentation.OTAValidatorComposable
import com.example.projetandroid.ui_layer.presentation.SignupComposable
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
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

                        composable<Dashboard>(
                            enterTransition = {
                                scaleIn()
                            },

                            exitTransition = {
                                scaleOut()
                            }
                        ) {
                            DashboardComposable(
                                navController = androidNavController,
                            )
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


@kotlinx.serialization.Serializable
object SplashScreen
