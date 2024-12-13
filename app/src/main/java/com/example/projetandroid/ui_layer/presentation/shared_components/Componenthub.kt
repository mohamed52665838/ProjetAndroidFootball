package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetandroid.R
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

@Composable
fun MessageComponent(userName: String, content: String, time: String) {
    Card {
        Column(
            Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = userName, style = MaterialTheme.typography.titleMedium)
            }
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 24.sp
                )
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text(text = time, style = MaterialTheme.typography.titleSmall, color = Color.Gray)
            }
        }
    }
}



@Composable
fun OwnMessageComponent( content: String, time: String) {
    Card {
        Column(
            Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()) {
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 24.sp
                )
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text(text = time, style = MaterialTheme.typography.titleSmall, color = Color.Gray)
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun MessageComponentPreview() {
    ProjetAndroidTheme {
        Column {
            MessageComponent(
                userName = "Amine Essid",
                content = "simple message message Hello World Hello World fa ga ga Hello World",
                time = "13:34"
            )
            Spacer(modifier = Modifier.height(4.dp))
            OwnMessageComponent(
                content = "simple message message Hello World Hello World fa ga ga Hello World",
                time = "13:34"
            )
        }

    }
}