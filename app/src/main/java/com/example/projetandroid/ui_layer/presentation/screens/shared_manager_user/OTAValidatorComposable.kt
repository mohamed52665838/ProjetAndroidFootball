package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.shared_components.PrimaryButton
import com.example.projetandroid.ui_layer.presentation.shared_components.SecondaryButton
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.primaryColor
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.OTACodeViewModelBase
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.OTACodeViewModelPreview


@Composable
fun OTAValidatorComposable(
    viewModel: OTACodeViewModelBase,
    navController: NavController
) {

    val uiState = viewModel.uiState.collectAsState(initial = UiState.Idle)
    Box(modifier = Modifier.fillMaxHeight(0.7f), contentAlignment = Alignment.Center) {
        HandleUIEvents(
            uiState = uiState.value,
            navController = navController,
            onDone = { viewModel.resetUiState() }, modifier = Modifier.zIndex(3f)
        )
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
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.takwira),
                contentDescription = "content",
                modifier = Modifier
                    .width(128.dp)
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(19.dp))
            Column(modifier = Modifier)
            {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    BasicTextField(
                        value = viewModel.codeOTA,
                        onValueChange = {
                            if (it.length <= 6) {
                                viewModel.codeOTA = it
                                if (it.length == 6) {
                                    viewModel.canSubmit = true
                                } else {
                                    viewModel.canSubmit = false
                                }
                            }
                        },
                        textStyle = TextStyle(color = Color.Transparent, fontSize = 0.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(Color.Black),
                        decorationBox = { innertext ->
                            Row {
                                repeat(6) { index ->
                                    Box(
                                        Modifier
                                            .padding(horizontal = 4.dp)
                                            .width(28.dp)
                                            .height(42.dp)
                                            .border(
                                                2.dp,
                                                color = primaryColor,
                                                shape = RoundedCornerShape(4.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (viewModel.codeOTA.length > index)
                                            Text(
                                                text = viewModel.codeOTA.getOrNull(index)
                                                    .toString(),
                                                color = primaryColor.copy(alpha = 0.7f)
                                            )
                                        else {
                                            if (viewModel.codeOTA.length == index) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(9.dp))
                                                        .size(18.dp)
                                                        .border(1.dp, color = Color.Black)
                                                )
                                                //innertext()
                                            } else {
                                                Text(
                                                    text = " ",
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    )
                }
                viewModel.errorMessage?.let {
                    Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color.Red)
                }
                TextButton(onClick = { }, contentPadding = PaddingValues(0.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                            contentDescription = "resend"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "resend", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(
                    onClick = { viewModel.verifyOTP() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.canSubmit
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_send_24),
                        contentDescription = "send OTA"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Send OTA", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(8.dp))
                SecondaryButton(
                    onClick = {
                        navController.navigate(SignIn) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.canCancel
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Cancel"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cancel", style = MaterialTheme.typography.titleMedium)
                }
            }


        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OTAValidatorComposablePreview() {
    ProjetAndroidTheme {
        OTAValidatorComposable(
            viewModel = OTACodeViewModelPreview(),
            navController = rememberNavController()
        )
    }
}
