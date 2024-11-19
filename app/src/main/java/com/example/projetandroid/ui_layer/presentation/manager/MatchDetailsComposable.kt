package com.example.projetandroid.ui_layer.presentation.manager

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.shared_components.TextLabel
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme

// manager view
@Composable
fun MatchDetailsComposable(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "back"
                )
            }
            Text("Match Details ", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // match address / number and date/time

        TextLabel(text = "General", modifier = Modifier.padding(vertical = 8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            InfoView(
                text = "Address afaffafafa fafafa",
                resId = R.drawable.baseline_location_on_24,
                modifier = Modifier.weight(1f)
            )
            InfoView(
                text = "Number",
                resId = R.drawable.baseline_groups_24,
                modifier = Modifier.weight(1f)
            )
            InfoView(
                text = "Date",
                resId = R.drawable.baseline_calendar_month_24,
                modifier = Modifier.weight(1f)
            )
        }
        TextLabel(text = "Time of Match", modifier = Modifier.padding(vertical = 8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("19:17")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = "time"
            )
        }
        TextLabel(text = "Participants List", modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.border(1.dp, Color.Gray)) {
            Row(Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = "Team A",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Team B",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            HorizontalDivider()
            Row(Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = "Amine Essid",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Sadd bon salah",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }


    }
}

@Composable
fun InfoView(text: String, resId: Int, modifier: Modifier = Modifier) {

    OutlinedCard(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxWidth(),
        ) {
            Icon(painter = painterResource(id = resId), contentDescription = "info view item")
            Text(text, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun MachDetailsPreview() {
    ProjetAndroidTheme {
        MatchDetailsComposable()
    }
}

