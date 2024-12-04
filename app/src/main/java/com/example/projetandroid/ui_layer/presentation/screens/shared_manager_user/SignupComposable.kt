package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.SignupFields
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.SupportUiStatusBox
import com.example.projetandroid.ui_layer.presentation.shared_components.AppPasswordTextFieldComposable
import com.example.projetandroid.ui_layer.presentation.shared_components.AppTextFieldComposable
import com.example.projetandroid.ui_layer.presentation.shared_components.DropdownComposableApp
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.PrimaryButton
import com.example.projetandroid.ui_layer.presentation.shared_components.SecondaryButton
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.SignUpViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.SignupViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.SignupViewModelPreview


@Composable
fun SignupComposable(
    navController: NavController,
    viewModel: SignUpViewModelBase = hiltViewModel<SignupViewModel>()
) {

    val uiState = viewModel.uiState.collectAsState(UiState.Idle)

    SupportUiStatusBox(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        controller = navController,
        uiStatus = uiState
    ) {
        Box(
            Modifier.padding(vertical = 32.dp), contentAlignment = Alignment.Center,
        ) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.trophy),
                alpha = 0.1f,
                contentDescription = "background"
            )
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            val focusManager = LocalFocusManager.current
            Box(modifier = Modifier.fillMaxSize()) {
                // event handler
                HandleUIEvents(
                    uiState = uiState.value,
                    modifier = Modifier.zIndex(3f),
                    onDone = { viewModel.restUiState() },
                    navController = navController
                )
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.trophy),
                    alpha = 0.1f,
                    contentDescription = "background"
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.takwira),
                        contentDescription = "content",
                        modifier = Modifier
                            .width(132.dp)
                    )
                    Text(
                        text = "Sign IN",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
//                    AppTextFieldComposable(
//                        value = viewModel.fields[0].value.value,
//                        onChange = viewModel.fields[0].onChange,
//                        error = viewModel.fields[0].onErrorMessage?.value,
//                        label = viewModel.fields[0].label,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    AppPasswordTextFieldComposable(
//                        value = viewModel.fields[1].value.value,
//                        onChange = viewModel.fields[1].onChange,
//                        error = viewModel.fields[1].onErrorMessage?.value,
//                        label = viewModel.fields[1].label,
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    DropdownComposableApp(value = viewModel.accountOption, onChange = {
                        viewModel.accountOption = it
                    }, options = viewModel.accountOptions, modifier = Modifier.fillMaxWidth())
                    viewModel.fields.map { (key, value) ->
                        if (key != SignupFields.PASSWORD)
                            AppTextFieldComposable(
                                iconId = value.iconId,
                                modifier = Modifier.fillMaxWidth(),
                                value = value.value.value,
                                error = value.onErrorMessage?.value,
                                onChange = value.onChange,
                                label = value.label,
                            )
                    }

                    AppPasswordTextFieldComposable(
                        modifier = Modifier.fillMaxWidth(),
                        iconId = viewModel.fields[SignupFields.PASSWORD]!!.iconId,
                        label = viewModel.fields[SignupFields.PASSWORD]!!.label,
                        error = viewModel.fields[SignupFields.PASSWORD]!!.onErrorMessage?.value,
                        value = viewModel.fields[SignupFields.PASSWORD]!!.value.value,
                        onChange = viewModel.fields[SignupFields.PASSWORD]!!.onChange
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryButton(onClick = {
                        focusManager.clearFocus()
                        viewModel.signUp()
                    }, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                                contentDescription = "go ahead"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Sign un", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(onClick = {
                        navController.navigate(SignIn)
                    }, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "go ahead",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Login", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SignupComposablePreview() {
    ProjetAndroidTheme {
        SignupComposable(
            navController = rememberNavController(),
            viewModel = SignupViewModelPreview()
        )
    }
}
