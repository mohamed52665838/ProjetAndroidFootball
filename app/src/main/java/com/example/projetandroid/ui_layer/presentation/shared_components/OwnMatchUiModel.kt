package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor
import java.text.SimpleDateFormat
import java.util.*

// Data Classes
data class OwnMatch(
    val labelOfTerrain: String,
    val date: String, // ISO 8601 Date Format
    val playersCount: Int,
    val price: Double
)

@Composable
fun MatchesUI(matches: List<OwnMatch>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        matches.forEach { match ->
            MatchCard(match)
        }
    }
}

@Composable
fun MatchCard(match: OwnMatch, onClick: (() -> Unit)? = null) {
    val dateTime = parseDate(match.date)

    Card(
        onClick = { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = tertiaryColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(match.labelOfTerrain, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Date: ${dateTime.first}",
                    fontSize = 14.sp,
                )
                Text(
                    text = "Time: ${dateTime.second}",
                    fontSize = 14.sp,
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Price: \$${match.price}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Players: ${match.playersCount}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMatchesUI1() {
//    val sampleMatches = listOf(
//        OwnMatch(
//            labelOfTerrain = "goback",
//            date = "2024-12-26T00:53:00.000Z",
//            playersCount = 2,
//            price =
//        ),
//        OwnMatch(
//            labelOfTerrain = "goback",
//            date = "2024-12-26T14:53:00.000Z",
//            playersCount = 3,
//            price = 200
//        )
//    )
    ProjetAndroidTheme {
    }
}
