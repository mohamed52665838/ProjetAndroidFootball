package com.example.projetandroid.ui_layer.presentation

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.projetandroid.Dashboard
import com.example.projetandroid.Fields
import com.example.projetandroid.R
import com.example.projetandroid.SignUp
import com.example.projetandroid.ui_layer.ui.theme.AppColors
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.ui.theme.containerColor
import com.example.projetandroid.ui_layer.ui.theme.primaryColor
import com.example.projetandroid.ui_layer.ui.theme.primaryColorVariant
import com.example.projetandroid.ui_layer.ui.theme.secondaryColor
import com.example.projetandroid.ui_layer.ui.theme.secondaryColorVariant
import com.example.projetandroid.ui_layer.ui.theme.surfaceColor
import com.example.projetandroid.ui_layer.viewModels.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun LoginComposable(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var isPasswordShow by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val screenState = viewModel.state


    screenState.value.errorMessage?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearState()
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearState()
                }) {
                    Text("OK")
                }
            },
            text = {
                Text(it)
            }
        )
    }

    screenState.value.data?.let {
        navController.navigate(Dashboard)
    }

    screenState.value.errorMessage?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearState()
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearState()
                }) {
                    Text("OK", style = MaterialTheme.typography.bodySmall)
                }
            },
            text = {
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
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
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceColor.copy(alpha = 0.95f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                val (logo, signIn, email, password, rememberMe, submitActions, divider, haventAccount) = createRefs()
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
                    text = "SIGNIN",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.constrainAs(signIn) {
                        top.linkTo(logo.bottom)
                        absoluteLeft.linkTo(logo.absoluteLeft)
                        absoluteRight.linkTo(logo.absoluteRight)
                    }
                )
                OutlinedTextField(
                    maxLines = 1,
                    placeholder = {
                        Text(text = "email, eg example@g.tn", color = primaryColorVariant[7])
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = ""
                        )
                    },
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    modifier = Modifier
                        .constrainAs(email) {
                            linkTo(top = parent.top, bottom = parent.bottom, bias = 0.35f)
                            absoluteLeft.linkTo(parent.absoluteLeft)
                            absoluteRight.linkTo(parent.absoluteRight)
                        }
                        .fillMaxWidth()
                )
                viewModel.errorMap[Fields.EMAIL]?.let {
                    Text("invalid email, check it", color = Color.Red)
                }
                OutlinedTextField(
                    value = viewModel.password,
                    leadingIcon = {
                        Icon(

                            painter = painterResource(id = R.drawable.baseline_lock_24),
                            contentDescription = ""
                        )

                    },
                    onValueChange = {
                        viewModel.password = it
                    },
                    placeholder = {
                        Text(text = "password", color = primaryColorVariant[7])
                    },
                    modifier = Modifier
                        .constrainAs(password) {
                            linkTo(top = email.bottom, bottom = parent.bottom, bias = 0.02f)
                        }
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isPasswordShow) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            contentDescription = "password",
                            modifier = Modifier.clickable { isPasswordShow = !isPasswordShow }
                        )
                    },
                    visualTransformation = if (isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
                )
                viewModel.errorMap[Fields.PASSWORD]?.let {
                    Text("password at least 6 characters", color = Color.Red)
                }
                Row(
                    modifier = Modifier.constrainAs(rememberMe) {
                        linkTo(start = parent.start, end = parent.end, bias = 0f)
                        linkTo(top = password.bottom, bottom = parent.bottom, bias = 0f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var isChecked by remember {
                        mutableStateOf(false)
                    }
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        modifier = Modifier,
                        colors = CheckboxDefaults.colors(
                            checkedColor = secondaryColor,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(text = "remember me", style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier
                    .constrainAs(submitActions) {
                        linkTo(top = rememberMe.bottom, bottom = parent.bottom, bias = 0f)
                    }
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = { viewModel.clearInput() },
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
                            viewModel.login()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "SIGNIN", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
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
                        navController.navigate(SignUp)
                    },
                    modifier = Modifier.constrainAs(haventAccount) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(bottom = parent.bottom, top = divider.top, bias = 0.1f)
                    }
                ) {
                    Text("haven't account ?", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        if (screenState.value.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


    }
}

@Composable
fun LoginComposableP(
) {

    var isPasswordShow by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }


    if (isShowAlertDialog) {
        AlertDialog(
            {
                isShowAlertDialog = false

            },
            {
                isShowAlertDialog = false
            },
            title = { Text("We got Data") },
            text = { Text("we have got all data right now") }
        )
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
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceColor.copy(alpha = 0.95f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                val (logo, signIn, email, password, rememberMe, submitActions, divider, haventAccount) = createRefs()
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
                    text = "SIGNIN",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.constrainAs(signIn) {
                        top.linkTo(logo.bottom)
                        absoluteLeft.linkTo(logo.absoluteLeft)
                        absoluteRight.linkTo(logo.absoluteRight)
                    }
                )
                OutlinedTextField(
                    placeholder = {
                        Text(text = "email, eg example@g.tn", color = primaryColorVariant[7])
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = ""
                        )
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
                    value = "",
                    leadingIcon = {
                        Icon(

                            painter = painterResource(id = R.drawable.baseline_lock_24),
                            contentDescription = ""
                        )

                    },
                    onValueChange = {},
                    placeholder = {
                        Text(text = "password", color = primaryColorVariant[7])
                    },
                    modifier = Modifier
                        .constrainAs(password) {
                            linkTo(top = email.bottom, bottom = parent.bottom, bias = 0.02f)
                        }
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                isPasswordShow = !isPasswordShow
                            },
                            painter = painterResource(id = if (isPasswordShow) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            contentDescription = "password"
                        )
                    },
                    visualTransformation = if (isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
                )
                Row(
                    modifier = Modifier.constrainAs(rememberMe) {
                        linkTo(start = parent.start, end = parent.end, bias = 0f)
                        linkTo(top = password.bottom, bottom = parent.bottom, bias = 0f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = true, onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = secondaryColor,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(text = "remember me", style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier
                    .constrainAs(submitActions) {
                        linkTo(top = rememberMe.bottom, bottom = parent.bottom, bias = 0f)
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
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                                contentDescription = "reset input",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "CLEAR", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                                contentDescription = "football",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "SIGNIN", style = MaterialTheme.typography.bodyMedium)
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
                    },
                    modifier = Modifier.constrainAs(haventAccount) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(bottom = parent.bottom, top = divider.top, bias = 0.1f)
                    }
                ) {
                    Text("haven't account ?", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginPreview() {
    ProjetAndroidTheme {
        LoginComposableP()
    }
}




