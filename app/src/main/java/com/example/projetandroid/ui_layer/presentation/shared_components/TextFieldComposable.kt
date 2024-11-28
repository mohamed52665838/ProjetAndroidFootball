package com.example.projetandroid.ui_layer.presentation.shared_components

import android.annotation.SuppressLint
import androidx.collection.emptyLongSet
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme


@Composable
fun AppTextFieldComposable(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (value: String) -> Unit,
    error: String? = null,
    label: String? = null,
    iconId: Int? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions? = null
) {


    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value, onValueChange = onChange,
            label = {
                label?.let {
                    Text(text = it)
                }
            },
            keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = if (iconId != null) {
                {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = ""
                    )
                }
            } else null
        )
        error?.let {
            Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Red)
        }
    }
}

@Composable
fun AppPasswordTextFieldComposable(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (value: String) -> Unit,
    error: String? = null,
    iconId: Int? = null,
    label: String? = null,
    keyboardOptions: KeyboardOptions? = null
) {

    var showPassword by remember {
        mutableStateOf(false)
    }

    Column {
        OutlinedTextField(
            modifier = modifier,
            leadingIcon = if (iconId != null) {
                { Icon(painter = painterResource(id = iconId), contentDescription = "") }
            } else null,
            value = value, onValueChange = onChange,
            label = {
                label?.let {
                    Text(text = it)
                }
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = showPassword.not() }) {
                    AnimatedContent(targetState = showPassword, label = "show password") {
                        if (it)
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                contentDescription = "hide password"
                            )
                        else
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_24),
                                contentDescription = "show password"
                            )
                    }
                }
            },
            keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
            visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        )
        error?.let {
            Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Red)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AppTextFieldComposablePreview() {

    var value by remember {
        mutableStateOf("")
    }

    ProjetAndroidTheme {
        Column {
            AppTextFieldComposable(
                iconId = R.drawable.baseline_account_circle_24,
                value = value,
                onChange = { value = it },
                label = "Hello There",
                modifier = Modifier.fillMaxWidth()
            )
            AppPasswordTextFieldComposable(
                value = value,
                onChange = { value = it },
                iconId = R.drawable.baseline_lock_24,
                label = "Hello There",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}