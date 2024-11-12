package com.example.projetandroid.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui.theme.primaryColorVariant
import com.example.projetandroid.ui.theme.secondaryColor
import com.example.projetandroid.ui.theme.surfaceColor


@Composable
fun SignupComposable(
    navController: NavController,
    modifier: Modifier = Modifier) {


        var isPasswordShow by rememberSaveable {
            mutableStateOf(false)
        }

        var isShowPasswordConfirmation by rememberSaveable {
            mutableStateOf(false)
        }

        if (false) {
            AlertDialog(
                {},
                {},
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
                        supportingText = {
                            if(false)
                                Text("HelloWorld")
                            else
                                Text("email should be valid")
                        },
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
                            if(false)
                                Text(text = "password invalid")
                            else
                                Text(text = "password at least 8 characters")
                        },
                        placeholder = {
                            Text(text = "password", color = primaryColorVariant[7] )
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
                        trailingIcon = { Icon(painter = painterResource(id = if(isPasswordShow) R.drawable.baseline_visibility_24 else  R.drawable.baseline_visibility_off_24), modifier = Modifier.clickable { isPasswordShow = !isPasswordShow }, contentDescription = "password" ) },
                        visualTransformation = if(isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
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

                        trailingIcon = { Icon(painter = painterResource(id = if(isShowPasswordConfirmation) R.drawable.baseline_visibility_24 else  R.drawable.baseline_visibility_off_24), modifier = Modifier.clickable { isShowPasswordConfirmation = !isShowPasswordConfirmation }, contentDescription = "password" ) },
                        visualTransformation = if(isShowPasswordConfirmation) VisualTransformation.None else PasswordVisualTransformation()

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
                                Icon(painter = painterResource(id = R.drawable.baseline_restart_alt_24), contentDescription = "reset input", modifier = Modifier.size(28.dp) )
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "SIGNUP", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "football", modifier = Modifier.size(28.dp) )
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
                        HorizontalDivider(modifier = Modifier.weight(1f).clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)), thickness = 4.dp)
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                            contentDescription = "icon"
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f).clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)), thickness = 4.dp)
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
fun SignupComposableP(modifier: Modifier = Modifier)
{

        var isPasswordShow by rememberSaveable {
            mutableStateOf(false)
        }

        var isShowPasswordConfirmation by rememberSaveable {
            mutableStateOf(false)
        }

        if (false) {
            AlertDialog(
                {},
                {},
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
                        supportingText = {
                                if(false)
                                    Text("HelloWorld")
                                else
                                    Text("email should be valid")
                        },
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
                            if(false)
                                Text(text = "password invalid")
                                else
                                Text(text = "password at least 8 characters")
                        },
                        placeholder = {
                            Text(text = "password", color = primaryColorVariant[7] )
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
                                trailingIcon = { Icon(painter = painterResource(id = if(isPasswordShow) R.drawable.baseline_visibility_24 else  R.drawable.baseline_visibility_off_24),  contentDescription = "password" ) },
                        visualTransformation = if(isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        placeholder = {
                            Text(text = "confirmation", color = primaryColorVariant[7])
                        },
                        value = "",
                        onValueChange = {
                            isPasswordShow = !isPasswordShow
                        },
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

                        trailingIcon = { Icon(painter = painterResource(id = if(isShowPasswordConfirmation) R.drawable.baseline_visibility_24 else  R.drawable.baseline_visibility_off_24),  contentDescription = "password" ) },
                        visualTransformation = if(isShowPasswordConfirmation) VisualTransformation.None else PasswordVisualTransformation()

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
                                Icon(painter = painterResource(id = R.drawable.baseline_restart_alt_24), contentDescription = "reset input", modifier = Modifier.size(28.dp) )
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "SIGNUP", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "football", modifier = Modifier.size(28.dp) )
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
                        HorizontalDivider(modifier = Modifier.weight(1f).clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)), thickness = 4.dp)
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                            contentDescription = "icon"
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f).clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)), thickness = 4.dp)
                    }
                    TextButton(
                        onClick = {
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


@Preview(showSystemUi = true)
@Composable
private fun SignupComposablePreview() {
    ProjetAndroidTheme {
        SignupComposableP()
    }
}
