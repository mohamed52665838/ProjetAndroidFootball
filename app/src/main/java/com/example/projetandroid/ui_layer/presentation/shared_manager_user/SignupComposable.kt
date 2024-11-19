package com.example.projetandroid.ui_layer.presentation.shared_manager_user

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.CodeOTP
import com.example.projetandroid.R
import com.example.projetandroid.SignUpFragments
import com.example.projetandroid.SignupFields
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.ui.theme.primaryColorVariant
import com.example.projetandroid.ui_layer.ui.theme.secondaryColor
import com.example.projetandroid.ui_layer.ui.theme.surfaceColor
import com.example.projetandroid.ui_layer.viewModels.SignupViewModel


@Composable
fun SignupComposableP(
    navController: NavController,
    modifier: Modifier = Modifier
) {


    var isPasswordShow by rememberSaveable {
        mutableStateOf(false)
    }

    var isShowPasswordConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier.padding(vertical = 32.dp), contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.trophy),
                contentDescription = "background"
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceColor.copy(alpha = 0.95f))
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                val (logo, signIn, email, password, passwordConfirmation, rememberMe, submitActions, divider, haventAccount) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.takwira),
                    contentDescription = "content",
                    modifier = Modifier
                        .width(128.dp)
                        .constrainAs(logo) {
                            linkTo(top = parent.top, bottom = email.top, bias = 0.6f)
                            absoluteLeft.linkTo(parent.absoluteLeft)
                            absoluteRight.linkTo(parent.absoluteRight)
                        }
                )
                Text(
                    text = "SIGNUP",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.constrainAs(signIn) {
                        top.linkTo(logo.bottom)
                        absoluteLeft.linkTo(logo.absoluteLeft)
                        absoluteRight.linkTo(logo.absoluteRight)
                    }
                )
                OutlinedTextField(
                    placeholder = {
                        Text(text = "email, eg mohamed@go.com", color = primaryColorVariant[7])
                    },
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .constrainAs(email) {

                            linkTo(top = parent.top, bottom = parent.bottom, bias = 0.35f)
                            absoluteLeft.linkTo(parent.absoluteLeft)
                            absoluteRight.linkTo(parent.absoluteRight)
                        }
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    supportingText = {
                        if (false)
                            Text(text = "password invalid")
                        else
                            Text(text = "password at least 8 characters")
                    },
                    placeholder = {
                        Text(text = "password", color = primaryColorVariant[7])
                    },
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .constrainAs(password) {
                            linkTo(
                                top = email.bottom,
                                bottom = parent.bottom,
                                bias = 0f,
                            )
                        }
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isPasswordShow) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            modifier = Modifier.clickable { isPasswordShow = !isPasswordShow },
                            contentDescription = "password"
                        )
                    },
                    visualTransformation = if (isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
                )
                OutlinedTextField(
                    placeholder = {
                        Text(text = "confirmation", color = primaryColorVariant[7])
                    },
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .constrainAs(passwordConfirmation) {
                            linkTo(start = parent.start, end = parent.end)
                            linkTo(
                                top = password.bottom,
                                bottom = parent.bottom,
                                bias = 0f,
                            )
                        }
                        .fillMaxWidth(),

                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isShowPasswordConfirmation) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            modifier = Modifier.clickable {
                                isShowPasswordConfirmation = !isShowPasswordConfirmation
                            },
                            contentDescription = "password"
                        )
                    },
                    visualTransformation = if (isShowPasswordConfirmation) VisualTransformation.None else PasswordVisualTransformation()

                )
                Row(modifier = Modifier
                    .constrainAs(submitActions) {
                        linkTo(
                            top = passwordConfirmation.bottom,
                            bottom = parent.bottom,
                            bias = 0f,
                            topMargin = 8.dp
                        )
                    }
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "CLEAR", style = MaterialTheme.typography.bodyMedium)
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

                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "SIGNUP", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "football",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.constrainAs(divider) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = submitActions.bottom, bottom = parent.bottom, bias = 0.2f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)),
                        thickness = 4.dp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                        contentDescription = "icon"
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)),
                        thickness = 4.dp
                    )
                }
                TextButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.constrainAs(haventAccount) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(bottom = parent.bottom, top = divider.top, bias = 0.1f)
                    }
                ) {
                    Text("Already Registered", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


@Composable
fun SignupComposable(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    var isPasswordShow by rememberSaveable {
        mutableStateOf(false)
    }

    var isShowPasswordConfirmation by rememberSaveable {
        mutableStateOf(false)
    }



    viewModel.currentScreenState.value.errorMessage?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearNetworkError()
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.clearNetworkError()
                }) {
                    Text("OK", style = MaterialTheme.typography.bodyMedium)
                }
            },
            text = {
                Text(text = it)
            }
        )
    }


    viewModel.currentScreenState.value.data?.let {
        navController.navigate(CodeOTP(email = viewModel.email))
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier.padding(vertical = 32.dp), contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.trophy),
                contentDescription = "background"
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceColor.copy(alpha = 0.95f))
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                val (logo, signIn, email, password, passwordConfirmation, rememberMe, submitActions, divider, haventAccount) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.takwira),
                    contentDescription = "content",
                    modifier = Modifier
                        .width(128.dp)
                        .constrainAs(logo) {
                            linkTo(top = parent.top, bottom = email.top, bias = 0.6f)
                            absoluteLeft.linkTo(parent.absoluteLeft)
                            absoluteRight.linkTo(parent.absoluteRight)
                        }
                )
                Text(
                    text = "SIGNUP",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.constrainAs(signIn) {
                        top.linkTo(logo.bottom)
                        absoluteLeft.linkTo(logo.absoluteLeft)
                        absoluteRight.linkTo(logo.absoluteRight)
                    }
                )
                AnimatedContent(targetState = viewModel.currentScreen.value, label = "",
                    modifier = Modifier.constrainAs(email) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        absoluteLeft.linkTo(parent.absoluteLeft)
                        absoluteRight.linkTo(parent.absoluteRight)
                    }
                ) { currentScreen ->
                    if (currentScreen == SignUpFragments.SECOND_FRAGMENT) {

                        Column(
                        ) {
                            OutlinedTextField(
                                placeholder = {
                                    Text(
                                        text = "email, eg mohamed@go.com",
                                        color = primaryColorVariant[7]
                                    )
                                },
                                value = viewModel.email,
                                onValueChange = {
                                    viewModel.email = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            viewModel.errorMap[SignupFields.EMAIL]?.let { error ->
                                Text(
                                    error,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            OutlinedTextField(
                                placeholder = {
                                    Text(text = "password", color = primaryColorVariant[7])
                                },
                                value = viewModel.password,
                                onValueChange = {
                                    viewModel.password = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = if (isPasswordShow) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                                        contentDescription = "password",
                                        modifier = Modifier.clickable {
                                            isPasswordShow = !isPasswordShow
                                        }
                                    )
                                },
                                visualTransformation = if (isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
                            )
                            viewModel.errorMap[SignupFields.PASSWORD]?.let { error ->
                                Text(
                                    error,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            OutlinedTextField(
                                placeholder = {
                                    Text(text = "confirmation", color = primaryColorVariant[7])
                                },
                                value = viewModel.passwordConfirmation,
                                onValueChange = {
                                    viewModel.passwordConfirmation = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),

                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = if (isShowPasswordConfirmation) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                                        contentDescription = "password",
                                        modifier = Modifier.clickable {
                                            isShowPasswordConfirmation = !isShowPasswordConfirmation
                                        }
                                    )
                                },
                                visualTransformation = if (isShowPasswordConfirmation) VisualTransformation.None else PasswordVisualTransformation()

                            )
                            viewModel.errorMap[SignupFields.CONFIRMATION]?.let { error ->
                                Text(
                                    error,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        viewModel.clearSecondScreen()
                                        viewModel.backFirstScreen()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.outlinedButtonColors()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "CLEAR",
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
                                        if (viewModel.validateSecondScreen()) {
                                            viewModel.submit()
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "SIGNUP",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_add_24),
                                            contentDescription = "football",
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column {
                            OutlinedTextField(
                                placeholder = {
                                    Text(text = "firstname", color = primaryColorVariant[7])
                                },
                                value = viewModel.name,
                                onValueChange = {
                                    viewModel.name = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            viewModel.errorMap[SignupFields.NAME]?.let {
                                Text(
                                    it,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                placeholder = {
                                    Text(text = "lastname", color = primaryColorVariant[7])
                                },
                                value = viewModel.lastname,
                                onValueChange = {
                                    viewModel.lastname = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            viewModel.errorMap[SignupFields.LASTNAME]?.let {
                                Text(
                                    it,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                placeholder = {
                                    Text(text = "phone number", color = primaryColorVariant[7])
                                },
                                value = viewModel.phoneNumber,
                                onValueChange = {
                                    viewModel.phoneNumber = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )
                            viewModel.errorMap[SignupFields.PHONE_NUMBER]?.let {
                                Text(
                                    it,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        navController.popBackStack()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.outlinedButtonColors()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                            contentDescription = "reset input",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "back",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = {
                                        viewModel.validateFirstScreen()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_send_24),
                                            contentDescription = "football",
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "SIGNUP",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }

                }

                Row(
                    modifier = Modifier.constrainAs(divider) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = email.bottom, bottom = parent.bottom, bias = 0.2f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)),
                        thickness = 4.dp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                        contentDescription = "icon"
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)),
                        thickness = 4.dp
                    )
                }
                TextButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.constrainAs(haventAccount) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(bottom = parent.bottom, top = divider.top, bias = 0.1f)
                    }
                ) {
                    Text("cancel registration", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        if (
            viewModel.currentScreenState.value.isLoading
        )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SignupComposablePreview() {
    ProjetAndroidTheme {
    }
}
