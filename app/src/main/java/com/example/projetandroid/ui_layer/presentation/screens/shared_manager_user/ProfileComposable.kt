package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.model.User
import com.example.projetandroid.ui_layer.presentation.SupportUiStatusBox
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.containerColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModelPreview
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.EditProfileFields
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.ProfileViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.ProfileViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.ProfileViewModelPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComposable(
    viewModel: DashboardViewModelBase,
    profileViewModel: ProfileViewModelBase,
    navController: NavController
) {


    val focusManager = LocalFocusManager.current
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


    val activityComposableChannel = profileViewModel.uiState.collectAsState(initial = UiState.Idle)

    var canEdit by remember {
        mutableStateOf(false)
    }

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
                        if (profileViewModel.isChanged())
                            IconButton(onClick = {
                                focusManager.clearFocus()
                                profileViewModel.update(viewModel::updateUser)
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_check_24),
                                    contentDescription = "update"
                                )
                            }
                        IconButton(onClick = {
                            canEdit = !canEdit
                            if (!canEdit) profileViewModel.restChanges()
                        }) {
                            Icon(
                                painter = painterResource(id = if (canEdit) R.drawable.baseline_clear_24 else R.drawable.baseline_edit_24),
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
        SupportUiStatusBox(uiStatus = activityComposableChannel, controller = navController) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
            ) {
                profileViewModel.fields.forEach { (_, value) ->
                    ProfileField(
                        value = value.value.value,
                        onChange = value.onChange,
                        enable = canEdit,
                        iconId = value.iconId,
                        errorMessage = value.onErrorMessage?.value,
                        label = value.label,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
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
    val user = User(
        name = "name",
        email = "email@email.com",
        active = true,
        lastName = "lastname",
        phone = "342452345",
        id = "simple id",
        role = "manager"
    )
    ProjetAndroidTheme {
        ProfileComposable(
            viewModel = DashboardViewModelPreview(),
            profileViewModel = ProfileViewModelPreview(user),
            navController = rememberNavController()
        )
    }
}