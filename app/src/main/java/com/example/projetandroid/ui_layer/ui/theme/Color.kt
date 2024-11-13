package com.example.projetandroid.ui_layer.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


object AppColors {
    // surface color
    val Bone = mapOf(
        "DEFAULT" to Color(0xFFD2CFC0),
        "100" to Color(0xFF2F2D21),
        "200" to Color(0xFF5E5A43),
        "300" to Color(0xFF8D8764),
        "400" to Color(0xFFB1AC91),
        "500" to Color(0xFFD2CFC0),
        "600" to Color(0xFFDBD9CD),
        "700" to Color(0xFFE4E3D9),
        "800" to Color(0xFFEDECE6),
        "900" to Color(0xFFF6F6F2)
    )

    // text color primary
    val RichBlack = mapOf(
        "DEFAULT" to Color(0xFF0A1D27),
        "100" to Color(0xFF020608),
        "200" to Color(0xFF040C10),
        "300" to Color(0xFF061218),
        "400" to Color(0xFF081820),
        "500" to Color(0xFF0A1D27),
        "600" to Color(0xFF1D5472),
        "700" to Color(0xFF308ABB),
        "800" to Color(0xFF6DB3D9),
        "900" to Color(0xFFB6D9EC)
    )

    // container color

    val DarkCyan = mapOf(
        "DEFAULT" to Color(0xFF429393),
        "100" to Color(0xFF0D1E1E),
        "200" to Color(0xFF1B3B3B),
        "300" to Color(0xFF285959),
        "400" to Color(0xFF357676),
        "500" to Color(0xFF429393),
        "600" to Color(0xFF5CB6B6),
        "700" to Color(0xFF85C8C8),
        "800" to Color(0xFFADDADA),
        "900" to Color(0xFFD6EDED)
    )

}

val primaryColor = Color(0xff000000)
val primaryColorVariant = (10 downTo 1).map { primaryColor.copy(alpha = it * 0.1f) }
val secondaryColor = Color.Green.copy(alpha = 0.8f)
val secondaryColorVariant = (10 downTo 1).map { secondaryColor.copy(alpha = it * 0.1f) }
val tertiaryColor = Color(0xFFFFD700)
val tertiaryColorVariant = (10 downTo 1).map { secondaryColor.copy(alpha = it * 0.1f) }
val containerColor = Color.White
val surfaceColor = Color(0xFFEDEDED)


