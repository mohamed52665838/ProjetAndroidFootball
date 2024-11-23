package com.example.projetandroid.ui_layer.presentation.shared_manager_user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packInts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.containerColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.EditProfileFields

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComposable(
    viewModel: DashboardViewModel,
    navController: NavController
) {

    var enableEdit by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearErrorMap()
            viewModel.clearUpdateState()
        }
    }
//    viewModel.state.value.errorMessage?.let {
//        AlertDialog(
//            onDismissRequest = {
//                viewModel.clearNetwork()
//            },
//            confirmButton = {
//                TextButton(onClick = { viewModel.clearNetwork() }) {
//                    Text(text = "OK", style = MaterialTheme.typography.bodyMedium)
//                }
//            },
//            text = {
//                Text(text = it, style = MaterialTheme.typography.bodyMedium)
//            }
//        )
//    }


    val activityComposableChannel = viewModel.profileUiState.collectAsState(initial = UiState.Idle)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Profile", style = MaterialTheme.typography.titleLarge)
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "return back"
                        )
                    }
                },
                actions = {
                    Row {
                        if (viewModel.isValueChanged())
                            IconButton(onClick = {
                                focusManager.clearFocus()
                                viewModel.updateUser()
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_check_24),
                                    contentDescription = "update"
                                )
                            }
                        IconButton(onClick = {
                            enableEdit = !enableEdit
                            if (!enableEdit) viewModel.clearUpdateState()
                        }) {
                            Icon(
                                painter = painterResource(id = if (enableEdit) R.drawable.baseline_clear_24 else R.drawable.baseline_edit_24),
                                contentDescription = "edit profile"
                            )
                        }

                        IconButton(onClick = {
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                                contentDescription = "add unset"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp)
        ) {
            viewModel.editProfileFieldsMap.map { key ->
                ProfileField(
                    errorMessage = viewModel.errorMap[key.key],
                    enable = enableEdit,
                    iconId = if (key.key == EditProfileFields.USERNAME || key.key == EditProfileFields.LASTNAME) R.drawable.baseline_person_outline_24 else R.drawable.baseline_smartphone_24,
                    label = key.key.name.lowercase().replaceFirstChar { it.uppercase() },
                    value = key.value.value,
                    onChange = viewModel.editProfileFieldsCallbacksMap[key.key]!!,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            ProfileField(
                iconId = R.drawable.baseline_email_24,
                label = "email".replaceFirstChar { it.uppercase() },
                value = viewModel.user?.email ?: "unset",
                onChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    when (activityComposableChannel.value) {
        is UiState.Error -> {
            AlertDialog(onDismissRequest = { viewModel.profileRestore() }, confirmButton = {
                TextButton(onClick = {
                    viewModel.profileRestore()
                }) {
                    Text(text = "OK")
                }
            },
                text = {
                    Text(text = (activityComposableChannel.value as UiState.Error).message)
                }
            )
        }

        is UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }

        is UiState.Success -> {

            AlertDialog(onDismissRequest = { viewModel.profileRestore() },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.profileRestore()
                    }) {
                        Text(text = "OK")
                    }
                },
                text = {
                    Text(text = (activityComposableChannel.value as UiState.Success).message)
                }
            )

        }

        is UiState.Idle -> {
            // empty
        }
    }
}

@Composable
fun ProfileField(
    value: String,
    onChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = false,
    errorMessage: String? = null,
    iconId: Int? = null,
    label: String? = null
) {
    Column {
        TextField(
            leadingIcon = {
                iconId?.let {
                    Icon(painter = painterResource(id = it), contentDescription = "icon")
                }
            },
            value = value,
            onValueChange = onChange,
            modifier = modifier,
            label = {
                label?.let {
                    Text(it)
                }
            },
            enabled = enable,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledLabelColor = Color.Black
            )
        )
        errorMessage?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.titleSmall)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ProfileComposablePreview() {
    ProjetAndroidTheme {
//        ProfileComposable()
    }
}