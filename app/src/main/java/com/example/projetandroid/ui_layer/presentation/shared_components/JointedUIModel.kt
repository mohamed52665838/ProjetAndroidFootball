package com.example.projetandroid.ui_layer.presentation.shared_components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

// Match Data Class
data class Match(
    val isAccepted: Boolean,
    val date: String,
    val playersCount: Int
)

@Composable
fun MatchesUIJointed(matches: List<Match>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        matches.forEach { match ->
            MatchJointedCard(match)
        }
    }
}

@Composable
fun MatchJointedCard(match: Match) {
    val dateTime = parseDate(match.date)

    Card(
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (match.isAccepted) Color(0xFFDFF8E1) else Color(0xFFFBEAEF)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = if (match.isAccepted) "Accepted" else "Pending",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (match.isAccepted) Color(0xFF388E3C) else Color(0xFFD32F2F)
                )
                Text(
                    text = "Date: ${dateTime.first}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Time: ${dateTime.second}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "Players: ${match.playersCount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

fun parseDate(isoDate: String): Pair<String, String> {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(isoDate) ?: Date()

        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        outputDateFormat.format(date) to outputTimeFormat.format(date)
    } catch (e: Exception) {
        "Invalid Date" to "Invalid Time"
    }
}

// Sample Preview
@Preview(showBackground = true)
@Composable
fun PreviewMatchesUI() {
    val sampleMatches = listOf(
        Match(isAccepted = false, date = "2024-12-26T00:53:00.000Z", playersCount = 2),
        Match(isAccepted = true, date = "2024-12-26T14:53:00.000Z", playersCount = 3)
    )
    MatchesUIJointed(matches = sampleMatches)
}
