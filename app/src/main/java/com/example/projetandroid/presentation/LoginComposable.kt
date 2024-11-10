package com.example.projetandroid.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetandroid.Fields
import com.example.projetandroid.R
import com.example.projetandroid.ui.theme.AppColors
import com.example.projetandroid.viewModels.LoginViewModel


@Composable
fun LoginComposable(
    viewModel: LoginViewModel = viewModel()
) {


    var isPasswordShow by rememberSaveable {
        mutableStateOf(false)
    }



    if (viewModel.weGotData.value) {
        AlertDialog(
            { viewModel.clear() },
            { viewModel.clear() },
            title = { Text("We got Data") },
            text = { Text("we have got all data right now") }
        )
    }

    Column(
        modifier = Modifier
            .background(color = AppColors.Bone["800"]!!)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign in", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.padding(vertical = 8.dp))
        Box {
            Image(
                painter = painterResource(R.drawable.project_logo),
                contentDescription = "application logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(32.dp))
            )
        }
        Spacer(Modifier.padding(vertical = 16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.email,
                {
                    viewModel.email = it
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_account_circle_24),
                        contentDescription = "account icon"
                    )
                },
                label = { Text("email") },
                modifier = Modifier.fillMaxWidth()
            )

            viewModel.errorMap[Fields.EMAIL]?.let {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(it, color = Color.Red, fontSize = 12.sp)
            }


            Spacer(Modifier.padding(vertical = 2.dp))

            OutlinedTextField(
                value = viewModel.password,
                {
                    viewModel.password = it
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_lock_24),
                        contentDescription = "password icon"
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(if (isPasswordShow) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                        contentDescription = "password toggle",

                        modifier = Modifier.clickable {
                            isPasswordShow = !isPasswordShow
                        }
                    )
                },
                label = { Text("password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = if (isPasswordShow) KeyboardType.Text else KeyboardType.Password),
                keyboardActions = KeyboardActions(),
                visualTransformation = if (isPasswordShow) VisualTransformation.None else PasswordVisualTransformation()
            )


            viewModel.errorMap[Fields.PASSWORD]?.let {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.padding(vertical = 8.dp))
            OutlinedButton(
                onClick = {
                    viewModel.submit()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit", color = AppColors.Bone["400"]!!)
            }
            OutlinedButton(
                onClick = {
                    viewModel.clear()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = AppColors.Bone["400"]!!,
                    contentColor = AppColors.Bone["100"]!!
                )
            ) {
                Text("Initialisation")
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        if (viewModel.isLoading.value) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = AppColors.Bone["400"]!!
            )
        } else {
            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 4.dp))

        }

        Text("haven't account yet ?", fontSize = 14.sp)
        Text(
            "create account",
            modifier = Modifier
                .clickable { }
                .padding(vertical = 8.dp),
            fontSize = 16.sp,
            color = AppColors.Bone["400"]!!
        )

    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginPreview() {
    LoginComposable()
}