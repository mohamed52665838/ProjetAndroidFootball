package com.example.projetandroid.ui_layer.shared_components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.R
import com.example.projetandroid.model.MatchModel
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme

@Composable
fun MatchViewPresentation(
    model: MatchModel,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClick: (id: String) -> Unit
) {
    OutlinedCard(
        onClick = {
            onClick(model.id)
        },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row {
                        Text(text = "Match", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = model.id,
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = "Location",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = model.address.uppercase(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row {
                        Icon( painter = painterResource(id = R.drawable.baseline_groups_24),
                            contentDescription = "Location",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${model.memberNumber} participants",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(text = "Date ${model.date}", style = MaterialTheme.typography.bodyMedium)
                }

                IconButton(onClick = { onClick(model.id) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.audit),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = "show details"
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MatchModelPresentationPreview() {
    val model = MatchModel(
        id = "xxxxxxxxxxxxxxxxxxxxx",
        memberNumber = 3,
        date = "11/10/2023",
        address = "simple address",
        false
    )


    ProjetAndroidTheme {
        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            MatchViewPresentation(model) {
            }
        }
    }

}


