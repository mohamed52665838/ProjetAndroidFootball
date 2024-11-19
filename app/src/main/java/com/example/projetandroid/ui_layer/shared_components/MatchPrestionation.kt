package com.example.projetandroid.ui_layer.shared_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme

@Composable
fun MatchPresentation(
    modifier: Modifier = Modifier
) {
    OutlinedCard(onClick = {}, modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            Text(text = "Match", style = MaterialTheme.typography.titleLarge )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MatchPresentationPreview() {
    ProjetAndroidTheme {
        Box(Modifier.padding(8.dp)) {
            MatchPresentation()
        }
    }
}
