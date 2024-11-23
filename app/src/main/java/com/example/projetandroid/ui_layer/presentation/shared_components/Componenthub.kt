package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme


@Composable
fun Heatmap(data: Map<String, List<Int>>, colorMapper: (Int) -> Color) {

    val ourMap = mutableMapOf<String, List<Int>>()

    Row {
        data.forEach { (key, value) ->
            Column {
                Text(text = key, style = MaterialTheme.typography.titleSmall)

                Row {
                    for (i in value.indices) {
                        Box(
                            modifier = Modifier
                                .size(20.dp) // Size of each cell
                                .background(colorMapper(value[i]))
                                .border(1.dp, Color.Gray) // Optional border
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HeatmapScreenPreview() {
    // Example data: a 2D array of numbers
    // show latest 3 month
    // lets say that we need to be min = 0, max = 8 matches
    // it mean that alphaChannel = numberOfMatches/8

    val mapDay = mapOf(
        "Jun" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
        "fev" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
        "mars" to listOf(1, 2, 3, 4, 5, 3, 4, 5, 2, 3, 2, 3, 5, 1, 5, 3, 1, 2, 4, 5, 6, 7, 8, 1, 2),
    )


    // Define a color mapping based on intensity
    val colorMapper: (Int) -> Color = { value ->
        when (value) {
            0 -> Color.Green.copy(alpha = 0f)
            1 -> Color.Green.copy(alpha = 0.1f)
            2 -> Color.Blue
            3 -> Color.Green
            4 -> Color.Yellow
            5 -> Color.Red
            else -> Color.Transparent
        }
    }

    ProjetAndroidTheme {
//        Heatmap(data = mapDay, colorMapper = colorMapper)
    }
}
