package com.example.projetandroid

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.ui_layer.presentation.screens.manager.AddSoccerFieldComposable
import com.example.projetandroid.ui_layer.presentation.screens.manager.ChatWithStewieComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.LoginComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.MapOfMatch
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.MatchComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.OTAValidatorComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.ProfileComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.SignupComposable
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables.DashboardScaffold
import com.example.projetandroid.ui_layer.presentation.screens.user.AddNowMatchComposable
import com.example.projetandroid.ui_layer.presentation.screens.user.ChatRoomScreen
import com.example.projetandroid.ui_layer.presentation.screens.user.MapAllSoccerFieldScreen
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.LoginViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.OTACodeViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.MatchViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.ProfileViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.SignupViewModel
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.ChatRoomViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.IOException
import javax.inject.Inject
import kotlin.reflect.typeOf


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDistination = mutableStateOf<Any?>(null)
        if (startDistination.value == null) {
            println("request has been sent")
            userRepository.currentUser().onEach {
                when (it) {
                    is Events.SuccessEvent -> {
                        println("here we go we have successfully got the user")
                        startDistination.value = Dashboard
                    }

                    is Events.ErrorEvent -> {
                        println("failed to got the user")
                        startDistination.value = SignIn
                    }

                    is Events.LoadingEvent -> {
                        println("Loading State")
                    }

                }
            }.catch {
                startDistination.value = SignIn
                println("failed to got the user: with exception")
            }.launchIn(lifecycleScope)
        }


        installSplashScreen().setKeepOnScreenCondition {
            startDistination.value == null
        }

        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)


        intent?.let {
            println(it.categories.toString())
        }

        enableEdgeToEdge()
        setContent {
            val androidNavController = rememberNavController()
            val currentBackStack by androidNavController.currentBackStackEntryAsState()
            val destination = currentBackStack?.destination
            ProjetAndroidTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                    }
                ) { innerPadding ->
                    startDistination.value?.let {
                        NavHost(
                            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                            navController = androidNavController,
                            startDestination = it
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
                                    val matchId = currentBackStack?.toRoute<MatchScreen>()
                                    val matchViewModel =
                                        hiltViewModel<MatchViewModel, MatchViewModel.MatchViewModelFactory>(
                                            creationCallback = { factory ->
                                                factory.create(matchId?.matchId ?: "noway")
                                            })
                                    MatchComposable(
                                        dashboardViewModel = viewModel,
                                        matchViewModel = matchViewModel,
                                        navController = androidNavController
                                    )
                                }
                                composable<MapScreen> {
                                    val matchId = currentBackStack?.toRoute<MapScreen>()
                                    val init_ = 0
                                    MapOfMatch(
                                        latitude = matchId?.lat ?: init_.toDouble(),
                                        long = matchId?.lon ?: init_.toDouble(),
                                        navController = androidNavController
                                    )
                                }

                                composable<RoomScreen> {
                                    val roomId = currentBackStack?.toRoute<RoomScreen>()
                                    roomId?.roomId?.let {

                                        val parentEntry = remember(it) {
                                            androidNavController.getBackStackEntry(Dashboard)
                                        }

                                        val viewModel: DashboardViewModel =
                                            hiltViewModel(parentEntry)


                                        val matchViewModel =
                                            hiltViewModel<ChatRoomViewModel, ChatRoomViewModel.ChatRoomFactory>(
                                                creationCallback = { factory ->
                                                    factory.create(it)
                                                })
                                        ChatRoomScreen(
                                            dashboardViewModel = viewModel,
                                            charRoomViewModel = matchViewModel,
                                            navController = androidNavController
                                        )
                                    }
                                }
                                composable<ListSoccerFieldScreenMap>(
                                    typeMap = mapOf(
                                        typeOf<List<LatLongSerializable>?>() to CustomListLatLongList.LatlongIdType
                                    )
                                ) {
                                    val listLongLat =
                                        currentBackStack?.toRoute<ListSoccerFieldScreenMap>()
                                    val init_ = 0
                                    MapAllSoccerFieldScreen(
                                        listOfSoccerField = listLongLat?.listLatLongSerializable
                                            ?: emptyList(),
                                        controller = androidNavController
                                    )
                                }


                                composable<ChatWithStewie> {
                                    ChatWithStewieComposable(
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

@Serializable
object Profile

@Serializable
object ChatWithStewie


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

@kotlinx.serialization.Serializable
data class RoomScreen(val roomId: String? = null)


@Serializable
data class LatLongSerializable(
    val latitude: Double,
    val longitude: Double,
    val _id: String
)

@kotlinx.serialization.Serializable
data class ListSoccerFieldScreenMap(
    val listLatLongSerializable: List<LatLongSerializable>? = null
)


object CustomTypeLatLong {
    val LatlongIdType = object : NavType<ListSoccerFieldScreenMap>(
        true
    ) {
        override fun get(bundle: Bundle, key: String): ListSoccerFieldScreenMap? {
            bundle.getString(key)?.let {
                return Json.decodeFromString(it)
            } ?: return null
        }

        override fun parseValue(value: String): ListSoccerFieldScreenMap {
            return Json.decodeFromString(Uri.decode(value))
        }


        override fun serializeAsValue(value: ListSoccerFieldScreenMap): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: ListSoccerFieldScreenMap) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }

}

object CustomListLatLongList {
    val LatlongIdType = object : NavType<List<LatLongSerializable>>(
        true
    ) {
        override fun get(bundle: Bundle, key: String): List<LatLongSerializable>? {
            bundle.getString(key)?.let {
                return Json.decodeFromString(it)
            } ?: return null
        }

        override fun parseValue(value: String): List<LatLongSerializable> {
            return Json.decodeFromString(Uri.decode(value))
        }


        override fun serializeAsValue(value: List<LatLongSerializable>): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: List<LatLongSerializable>) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }

}
