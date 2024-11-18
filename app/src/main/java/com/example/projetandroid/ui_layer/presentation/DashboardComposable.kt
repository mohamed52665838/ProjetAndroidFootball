package com.example.projetandroid.ui_layer.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.model.User
import com.example.projetandroid.ui_layer.presentation.shared_components.TextLabel
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.ui.theme.containerColor
import com.example.projetandroid.ui_layer.ui.theme.primaryColor
import com.example.projetandroid.ui_layer.ui.theme.secondaryColor
import com.example.projetandroid.ui_layer.ui.theme.tertiaryColor
import com.example.projetandroid.ui_layer.viewModels.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


enum class DashboardState {
    HOME_FRAGMENT,
    ACTIVITY_FRAGMENT,
    PROFILE_FRAGMENT
}


// dashboard are represent by 3 fragments


@Composable
fun DashboardComposable(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController
) {


    val localContext = LocalContext.current as Activity

    var currentFragment by rememberSaveable {
        mutableStateOf(DashboardState.HOME_FRAGMENT)
    }

    var isShowAlertOfDelete by remember {
        mutableStateOf(false)
    }


    var isLogoutAlertShow by remember {
        mutableStateOf(false)
    }


    if (viewModel.isAccountDeleted.value) {
        viewModel.clearContext()
        navController.navigate(SignIn) {
            popUpTo(SignIn)
        }
    }


    viewModel.state.value.errorMessage?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearNetwork()
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearNetwork()
                }) {
                    Text("OK")
                }
            },
            text = {
                Text(it)
            }
        )
    }

    var credentialWords by remember {
        mutableStateOf("")
    }

    if (isLogoutAlertShow) {
        AlertDialog(onDismissRequest = { isLogoutAlertShow = true }, confirmButton = {
            TextButton(onClick = {
                viewModel.clearContext()
                navController.navigate(SignIn) {
                    popUpTo(SignIn)
                }
            }) {
                Text(text = "logout", style = MaterialTheme.typography.bodyMedium)
            }
        },
            dismissButton = {
                TextButton(onClick = {
                    isLogoutAlertShow = false
                }) {
                    Text(text = "cancel", style = MaterialTheme.typography.bodyMedium)
                }
            },
            text = {
                Text(text = "are you sure you wanna logout ?")
            }
        )
    }

    if (isShowAlertOfDelete) {
        AlertDialog(
            onDismissRequest = {
                isShowAlertOfDelete = false
                credentialWords = ""
            },
            dismissButton = {
                TextButton(onClick = {
                    isShowAlertOfDelete = false
                    credentialWords = ""
                }) {
                    Text("cancel", style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearNetwork()
                    if (credentialWords == "I want to delete my account") {
                        viewModel.delete()
                    }


                }) {
                    Text("delete", style = MaterialTheme.typography.bodyMedium)
                }
            },
            text = {
                Column {
                    Text("Are you sure to delete your account? if so, write 'I want to delete my account'")
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = credentialWords, onValueChange = {
                            credentialWords = it
                        },
                        Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }


    val localFocusManager = LocalFocusManager.current
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
        Box(Modifier.padding(it)) {
            viewModel.user?.let {
                AnimatedContent(targetState = currentFragment, label = "") { currentFragment ->


                    when (currentFragment) {

                        // home fragment ( dashboard home screen )
                        DashboardState.HOME_FRAGMENT -> {
                            Text(
                                text = "Welcome ${it.name.capitalize(Locale.ROOT)}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        // activity fragment ( dashboard home screen ) => explore all matches available

                        DashboardState.ACTIVITY_FRAGMENT -> {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Activity Section",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                        // profile fragment => explore existent profile info and update

                        DashboardState.PROFILE_FRAGMENT -> {
                            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Profile ${it.name}")
                                    Button(
                                        onClick = {
                                            isLogoutAlertShow = true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = primaryColor.copy(alpha = 0.4f)
                                        )
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_logout_24),
                                            contentDescription = "logout"
                                        )
                                    }
                                }
                                Text(text = "Wanna Update? ${it.name}")
                                Spacer(modifier = Modifier.height(8.dp))
                                Column {
                                    OutlinedTextField(
                                        value = viewModel.username,
                                        onValueChange = { viewModel.username = it },
                                        Modifier.fillMaxWidth(),
                                        placeholder = { Text(text = "name") })
                                    Spacer(modifier = Modifier.height(4.dp))
                                    OutlinedTextField(
                                        value = it.email,
                                        onValueChange = {},
                                        Modifier.fillMaxWidth(),
                                        placeholder = { Text(text = "email") },
                                        enabled = false
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    OutlinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = viewModel.phoneNumber,
                                        onValueChange = { viewModel.phoneNumber = it },
                                        placeholder = { Text(text = "phone Number") })
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            OutlinedButton(
                                                onClick = {},
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.outlinedButtonColors()
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        text = "RESET",
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                                                        contentDescription = "reset input",
                                                        modifier = Modifier.size(28.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Button(
                                                onClick = {
                                                    viewModel.updateUser()
                                                    localFocusManager.clearFocus()
                                                },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        text = "UPDATE",
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                                                        contentDescription = "football",
                                                        modifier = Modifier.size(28.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    HorizontalDivider(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp), thickness = 2.dp
                                    )
                                    Text(
                                        text = "Danger zone",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    TextButton(onClick = {
                                        isShowAlertOfDelete = true
                                    }, contentPadding = PaddingValues(0.dp)) {
                                        Text(
                                            "delete account",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }

                                }

                            }
                        }
                    }
                }


            }

            viewModel.state.value.errorMessage?.let {
                AlertDialog(
                    onDismissRequest = {
                    },
                    confirmButton = {
                        TextButton(onClick = {
                        }) {
                            Text("OK")
                        }
                    },
                    text = {
                        Text(it)
                    }
                )
            }

            if (viewModel.state.value.isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (viewModel.state.value.successMessage.isNotEmpty()) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.clearNetwork()
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.clearNetwork()
                        }) {
                            Text("OK")
                        }
                    },
                    text = {
                        Text(viewModel.state.value.successMessage)
                    }
                )
            }
        }
    }
}


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
                    Text(
                        text = "Welcome ${it.name.capitalize(Locale.ROOT)}",
                        style = MaterialTheme.typography.titleLarge
                    )
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
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
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(6.dp)))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "tomorrow's")
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(6.dp)))
                    Spacer(modifier = Modifier.height(8.dp))
                    TextLabel(text = "Soccer Pitch Status")

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.soccer_field), contentDescription = "soccer Pitch Location", modifier = Modifier
                                .size(28.dp)
                                .padding(end = 4.dp))
                            Text("Soccer Field name",)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.baseline_location_on_24), contentDescription = "soccer Pitch Location", modifier = Modifier
                                .size(28.dp)
                                .padding(end = 4.dp))
                            Text("Random, address")
                        }
                    }

                }
            }
        }
    }
}


@Preview
@Composable
private fun DBHomeManagerPreview() {
    val user = User(
        id = "id",
        name = "Mohamed",
        lastName = "Dhaouadi",
        phone = "5478965",
        password = "password",
        active = true,
        email = "mohamed@go.com",
    )
    ProjetAndroidTheme {
        DashboardHomeManagerComposablePreview(user = user)
    }
}


@Composable
fun Heatmap(data: Map<String, List<Int>>, colorMapper: (Int) -> Color) {

    val ourMap = mutableMapOf<String, List<Int>>()

    Row {
        data.forEach { (key, value) ->
            Column {
                Text(text = key, style = MaterialTheme.typography.titleSmall)

                Row {
                    for (i in value.indices) {
                        Box(
                            modifier = Modifier
                                .size(20.dp) // Size of each cell
                                .background(colorMapper(value[i]))
                                .border(1.dp, Color.Gray) // Optional border
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HeatmapScreenPreview() {
    // Example data: a 2D array of numbers
    // show latest 3 month
    // lets say that we need to be min = 0, max = 8 matches
    // it mean that alphaChannel = numberOfMatches/8

    val mapDay = mapOf(
        "Jun" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
        "fev" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
        "mars" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
    )


    // Define a color mapping based on intensity
    val colorMapper: (Int) -> Color = { value ->
        when (value) {
            0 -> Color.Green.copy(alpha = 0f)
            1 -> Color.Green.copy(alpha = 0.1f)
            2 -> Color.Blue
            3 -> Color.Green
            4 -> Color.Yellow
            5 -> Color.Red
            else -> Color.Transparent
        }
    }

    ProjetAndroidTheme {
        Heatmap(data = mapDay, colorMapper = colorMapper)
    }
}

// list heatmap is 0 -> 1