package com.example.projetandroid.ui_layer.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.ui_layer.ui.theme.containerColor
import com.example.projetandroid.ui_layer.ui.theme.primaryColor
import com.example.projetandroid.ui_layer.ui.theme.secondaryColor
import com.example.projetandroid.ui_layer.viewModels.DashboardViewModel


enum class DashboardState {
    HOME_FRAGMENT,
    ACTIVITY_FRAGMENT,
    PROFILE_FRAGMENT
}


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
                Text(text = "logout")
            }
        },
            dismissButton = {
                TextButton(onClick = {
                    isLogoutAlertShow = false
                }) {
                    Text(text = "cancel")
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
                        DashboardState.HOME_FRAGMENT -> {
                            Text(
                                text = "Welcome ${it.name.capitalize()}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        DashboardState.ACTIVITY_FRAGMENT -> {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Activity Section",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

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