package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetandroid.R
import com.example.projetandroid.model.match.matchModel.PlayersOfMatch
import com.example.projetandroid.model.match.matchModel.UserId
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import kotlin.math.truncate

@Composable
fun PlayerOfMatchUiModel(
    playersOfMatch: PlayersOfMatch,
    isShowSuggestions: Boolean,
    onAccept: (() -> Unit)? = null,
    onRefuse: (() -> Unit)? = null
) {
    Column(
        Modifier
            .background(Color(0xffeeeeee))
            .padding(
                horizontal = 8.dp,
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = playersOfMatch.userId.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = playersOfMatch.matchId,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xff000000).copy(alpha = 0.4f),
                    fontSize = 12.sp
                )
            }

            if (isShowSuggestions) {
                if (!playersOfMatch.isAccepted)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            onAccept?.invoke()
                        }) {
                            Icon(
                                painterResource(id = R.drawable.baseline_check_24),
                                contentDescription = "accept"
                            )
                        }
                        IconButton(onClick = { onRefuse?.invoke() }) {
                            Icon(
                                painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = "accept"
                            )
                        }
                    }
                else {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_check_circle_24),
                        contentDescription = "accepted"
                    )
                }
            } else {
                if (playersOfMatch.isAccepted.not()) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "accepted"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_check_circle_24),
                        contentDescription = null
                    )
                }
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
private fun PlayerOfMatchModelComposablePreview() {


    ProjetAndroidTheme {
        PlayerOfMatchUiModel(
            playersOfMatch = PlayersOfMatch(
                _id = "helloWorld",
                userId = UserId("simple_id", name = "name"),
                matchId = "match",
                isAccepted = false
            ), isShowSuggestions = true
        )
    }
}
