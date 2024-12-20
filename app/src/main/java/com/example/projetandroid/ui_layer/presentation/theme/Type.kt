package com.example.projetandroid.ui_layer.presentation.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetandroid.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
    ),

    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cairo_black)),
        fontWeight = FontWeight.Normal,
        fontSize = 6.sp,
    ),
)


@Preview(showSystemUi = true)
@Composable
private fun TryFonts() {

    Column {
        Text("Title:", style = Typography.titleSmall)
        Text("Title Large", style = Typography.titleLarge)
        Text("Title Medium", style = Typography.titleMedium)
        Text("Title Small", style = Typography.titleSmall)
        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
        Text("Body:", style = Typography.bodySmall)
        Text("Body Large", style = Typography.bodyLarge)
        Text("Body Medium", style = Typography.bodyMedium)
        Text("Body Small", style = Typography.bodySmall)
        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

        Text("Labels:", style = Typography.labelSmall)
        Text("Label Large", style = Typography.labelLarge)
        Text("Label Medium", style = Typography.labelMedium)
        Text("Label Small", style = Typography.labelSmall)
    }
}

