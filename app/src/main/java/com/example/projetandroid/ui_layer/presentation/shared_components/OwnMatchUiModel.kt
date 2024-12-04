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
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.presentation.theme.tertiaryColor
import java.text.SimpleDateFormat
import java.util.*

// Data Classes
data class OwnMatch(
    val labelOfTerrain: String,
    val date: String, // ISO 8601 Date Format
    val playersCount: Int,
    val price: Double,
    val isMine: Boolean = false
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
            MatchCard(match = match)
        }
    }
}

@Composable
fun MatchCard(
    modifier: Modifier = Modifier,
    match: OwnMatch, onClick: (() -> Unit)? = null
) {
    val dateTime = parseDate(match.date)

    ElevatedCard(
        onClick = { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                        Text(
                            match.labelOfTerrain.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }
                    if (match.isMine)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_person_outline_24),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "mine",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )
                        }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = null
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Time",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = dateTime.second,
                                fontSize = 14.sp,
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = null
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Date",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = dateTime.first,
                                fontSize = 14.sp,
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_outline_24),
                            contentDescription = null
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (match.playersCount + 1).toString(),
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Members",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_price_check_24),
                            contentDescription = null
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = match.price.toString() + " $",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Join price",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
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
    val match = OwnMatch(
        labelOfTerrain = "goback",
        date = "2024-12-26T14:53:00.000Z",
        playersCount = 3,
        price = 200.0,
        isMine = true
    )
    ProjetAndroidTheme {
        Column {
            MatchCard(match = match)
            MatchCard(match = match)
        }
    }
}
