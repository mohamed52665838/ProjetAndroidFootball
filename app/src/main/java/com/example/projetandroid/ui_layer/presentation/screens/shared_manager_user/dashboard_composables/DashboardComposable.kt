package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables

import android.app.Activity
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetandroid.AddSoccerField
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.model.User
import com.example.projetandroid.model.manager.Statistics
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.TextLabel
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.secondaryColor
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.HomeManagerViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import java.util.Locale


enum class DashboardState {
    HOME_FRAGMENT,
    ACTIVITY_FRAGMENT,
    PROFILE_FRAGMENT
}


// dashboard are represent by 3 fragments


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardComposable(
    viewModel: DashboardViewModelBase,
    managerViewModel: HomeManagerViewModel,
    navController: NavController
) {

    val localContext = LocalContext.current as Activity
    val currentUser = viewModel.user
    val currentSoccerField = managerViewModel.soccerFieldWatcher.value
    val activityComposableChannel = viewModel.uiState.collectAsState(initial = UiState.Idle)

    HandleUIEvents(
        uiState = activityComposableChannel.value,
        navController = navController,
        onDone = { viewModel.restUiState() }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Dashboard")
            },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_settings_24),
                            contentDescription = "go back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column {

                currentUser.value?.let {
                    Text(
                        text = "Welcome ${it.name.capitalize(Locale.ROOT)}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                currentSoccerField?.let {
                } ?: kotlin.run {
                }
            }
        }

    }


    // event  handling (loading, ...)
}


// dashboard home section preview
@Composable
fun DashboardHomeManagerComposablePreview(
    user: User?
) {

    val localFocusManager = LocalFocusManager.current
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = secondaryColor,
                        selectedIconColor = Color.White
                    ),
                    selected = true,
                    onClick = {
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
                    onClick = {
                    },
                    selected = false,
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
                    selected = false,
                    onClick = {
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
        Box(Modifier.padding(it)) {
            user?.let {
                Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("19 Nov 2024", style = MaterialTheme.typography.titleSmall)
//                    Text(LocalDate.of(2024, 10, 11) .format(DateTimeFormatter.ofPattern("d MMM muumuu")), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Color.Gray)
                    ) {}

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("less", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.width(8.dp))
                        for (i in 0..4) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(16.dp)
                                    .background(Color.Green.copy(alpha = i / 4f))
                                    .border(1.dp, Color.Green, RoundedCornerShape(2.dp))
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("more", style = MaterialTheme.typography.titleSmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    TextLabel(text = "Matches time line")
                    Text(text = "today's")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "tomorrow's")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextLabel(text = "Soccer Pitch Status")

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.soccer_field),
                                contentDescription = "soccer Pitch Location",
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(end = 4.dp)
                            )
                            Text("Soccer Field name")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_location_on_24),
                                contentDescription = "soccer Pitch Location",
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(end = 4.dp)
                            )
                            Text("Random, address")
                        }
                    }

                }
            }
        }
    }
}


// dashboard activity section manager

@Composable
fun DashboardActivitySectionManager(user: User?, modifier: Modifier = Modifier) {
    """
       Section display the list of requested Matched ( confirmed, not confirmed ) by one condition
       if all players are ready 10 Players in our case
    """.trimIndent()

    val activity = LocalContext.current as Activity

    var isAlertFilterDateShow by rememberSaveable {
        mutableStateOf(false)
    }

    var isAlertFilterGeneralPurposeShow by rememberSaveable {
        mutableStateOf(false)
    }

    val datePickerDialog = DatePickerDialog(
        activity,
        { _, year, month, dayOfMonth -> println("here we go $year $dayOfMonth $month"); },
        2023,
        10,
        11
    )


    val localFocusManager = LocalFocusManager.current
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = secondaryColor,
                        selectedIconColor = Color.White
                    ),
                    selected = false,
                    onClick = {
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
                    onClick = {
                    },
                    selected = true,
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
                    selected = false,
                    onClick = {
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
        Box(
            Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            user?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // list matches by default sorted by date
                    // filter get all matches of specific date
                    // get all matches of with status ( confirmed, not confirmed )
                    Text(text = "Matches", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        datePickerDialog.show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = "calendar filter"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_alt_24),
                            contentDescription = "filter general purpose"
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun DashboardActivitySectionManagerPreview() {
    val user = User(
        id = "id",
        name = "Mohamed",
        lastName = "Dhaouadi",
        phone = "5478965",
        active = true,
        email = "mohamed@go.com",
        role = "user"
    )
    ProjetAndroidTheme {
        DashboardActivitySectionManager(user = user)
    }
}


@Composable
fun DBHomeManagerPreview() {
    val user = User(
        id = "id",
        name = "Mohamed",
        lastName = "Dhaouadi",
        phone = "5478965",
        active = true,
        email = "mohamed@go.com",
        role = "user"
    )
    ProjetAndroidTheme {
        DashboardHomeManagerComposablePreview(user = user)
    }
}


@Composable
fun TerrainItem(modifier: Modifier = Modifier, text: String, icon: Int) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "soccer Pitch Location",
            modifier = Modifier
                .size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}


// statistics part view


@Preview(showSystemUi = true)
@Composable
private fun StatisticsPreview() {
//    val statistics = Statistics(5 * 20, 5)
    ProjetAndroidTheme {
    }
}
