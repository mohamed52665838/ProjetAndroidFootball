package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.ui_layer.presentation.theme.primaryColor
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable (() -> Unit),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = tertiaryColor),
        enabled = enabled
    ) {
        content()
    }

}


@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable (() -> Unit),
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = primaryColor
        ),
        border = BorderStroke(1.dp, tertiaryColor),
        modifier = modifier,
        enabled = enabled
    ) {
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrimaryButtonColor() {
    Column {
        SecondaryButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("hello there")
        }
        PrimaryButton(onClick = { println("clicked") }) {
            Text(text = "hello there")
        }
    }
}


