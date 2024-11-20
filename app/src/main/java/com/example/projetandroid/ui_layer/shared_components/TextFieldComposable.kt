package com.example.projetandroid.ui_layer.shared_components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme


@Composable
fun AppTextFieldComposable(
    modifier: Modifier =  Modifier,
    value: String,
    onChange: (value: String) -> Unit,
    error: String? = null,
    label: String? = null
) {


    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value, onValueChange = onChange,
            label = {
                label?.let {
                    Text(text = it)
                }
            }
            )
        error?.let {
            Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Red)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun AppTextFieldComposablePreview() {
    ProjetAndroidTheme {
        AppTextFieldComposable(value = "Hello There", onChange = {}, label = "Hello There", modifier = Modifier.fillMaxWidth())
    }
}