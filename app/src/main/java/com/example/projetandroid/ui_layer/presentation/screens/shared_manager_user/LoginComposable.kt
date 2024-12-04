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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.SignUp
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.SupportUiStatusBox
import com.example.projetandroid.ui_layer.presentation.shared_components.AppPasswordTextFieldComposable
import com.example.projetandroid.ui_layer.presentation.shared_components.AppTextFieldComposable
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.PrimaryButton
import com.example.projetandroid.ui_layer.presentation.shared_components.SecondaryButton
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.LoginViewModel
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.LoginViewModelBase
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


@Composable
fun LoginComposable(
    navController: NavController,
    viewModel: LoginViewModelBase = hiltViewModel<LoginViewModel>()
) {

    val focusManager = LocalFocusManager.current

    SupportUiStatusBox(
        modifier = Modifier.fillMaxSize(),
        uiStatus = viewModel.uiStateFlowWatcher.collectAsState(),
        controller = navController
    ) {

        // event handler
        Image(
            modifier = Modifier.fillMaxSize(),
            painter =
            painterResource(id = R.drawable.trophy),
            alpha = 0.1f,
            contentDescription = null
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
            AppTextFieldComposable(
                iconId = viewModel.fields[0].iconId,
                value = viewModel.fields[0].value.value,
                onChange = viewModel.fields[0].onChange,
                error = viewModel.fields[0].onErrorMessage?.value,
                label = viewModel.fields[0].label,
                modifier = Modifier.fillMaxWidth()
            )
            AppPasswordTextFieldComposable(
                iconId = viewModel.fields[1].iconId,
                value = viewModel.fields[1].value.value,
                onChange = viewModel.fields[1].onChange,
                error = viewModel.fields[1].onErrorMessage?.value,
                label = viewModel.fields[1].label,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {}, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "remember me", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(onClick = remember {
                {
                    focusManager.clearFocus()
                    viewModel.login()
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                        contentDescription = "go ahead"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Go ahead", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SecondaryButton(onClick = remember {
                {
                    navController.navigate(SignUp)
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.soccer_player),
                        contentDescription = "go ahead",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Get Started", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginPreview() {
    ProjetAndroidTheme {
//        LoginComposable(
//            navController = rememberNavController(),
//            viewModel = LoginViewModelPreview()
//        )
    }
}




