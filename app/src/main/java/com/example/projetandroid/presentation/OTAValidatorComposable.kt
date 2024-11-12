package com.example.projetandroid.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.projetandroid.R
import com.example.projetandroid.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui.theme.primaryColor
import com.example.projetandroid.ui.theme.primaryColorVariant
import com.example.projetandroid.ui.theme.secondaryColor
import com.example.projetandroid.ui.theme.surfaceColor



@Composable
fun OTAValidatorComposable(modifier: Modifier = Modifier) {

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
                val (logo, signIn, email, middleLayout, lastLayout, rememberMe, submitActions, divider, haventAccount) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.takwira),
                    contentDescription = "content",
                    modifier = Modifier
                        .width(128.dp)
                        .constrainAs(logo) {
                            linkTo(top = parent.top, bottom = middleLayout.top, bias = 0.4f)
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


                val state = remember { mutableStateOf(TextFieldValue()) }
                Column(modifier = Modifier.constrainAs(middleLayout) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.3f)
                }) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        BasicTextField(
                            value = state.value,
                            onValueChange = {
                                if (it.text.length <= 4)
                                    state.value = it
                            },
                            textStyle = TextStyle(color = Color.Transparent, fontSize = 0.sp),
                            cursorBrush = SolidColor(Color.Black),
                            decorationBox = { innertext ->

                                Row {
                                    repeat(4) { index ->
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
                                            if (state.value.text.length > index)
                                                Text(
                                                    text = state.value.text.getOrNull(index)
                                                        .toString(),
                                                    color = primaryColor.copy(alpha = 0.7f)
                                                )
                                            else {
                                                if (state.value.text.length == index) {
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
                                                        color = primaryColor.copy(alpha = 0.7f)
                                                    )
                                                }
                                            }

                                        }
                                    }

                                }
                            }
                        )
                    }
                    TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
                       Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.baseline_restart_alt_24), contentDescription = "resend")
                           Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "resend", style = MaterialTheme.typography.bodyMedium)
                       }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        OutlinedButton(
                            onClick = { /*TODO*/ },
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
                                Text(text = "CANCEL", style = MaterialTheme.typography.bodyMedium)
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
                }

                }

                }
            }
    }


@Composable
fun OTAValidatorComposableP(modifier: Modifier = Modifier) {

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
                val (logo, signIn, email, middleLayout, lastLayout, rememberMe, submitActions, divider, haventAccount) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.takwira),
                    contentDescription = "content",
                    modifier = Modifier
                        .width(128.dp)
                        .constrainAs(logo) {
                            linkTo(top = parent.top, bottom = middleLayout.top, bias = 0.4f)
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


                val state = remember { mutableStateOf(TextFieldValue()) }
                Column(modifier = Modifier.constrainAs(middleLayout) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.3f)
                }) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        BasicTextField(
                            value = state.value,
                            onValueChange = {
                                if (it.text.length <= 4)
                                    state.value = it
                            },
                            textStyle = TextStyle(color = Color.Transparent, fontSize = 0.sp),
                            cursorBrush = SolidColor(Color.Black),
                            decorationBox = { innertext ->

                                Row {
                                    repeat(4) { index ->
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
                                            if (state.value.text.length > index)
                                                Text(
                                                    text = state.value.text.getOrNull(index)
                                                        .toString(),
                                                    color = primaryColor.copy(alpha = 0.7f)
                                                )
                                            else {
                                                if (state.value.text.length == index) {
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
                    TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.baseline_restart_alt_24), contentDescription = "resend")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "resend", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        OutlinedButton(
                            onClick = { /*TODO*/ },
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
                                Text(text = "CANCEL", style = MaterialTheme.typography.bodyMedium)
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
                }

            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OTAValidatorComposablePreview() {
    ProjetAndroidTheme {
        OTAValidatorComposableP()
    }
}
