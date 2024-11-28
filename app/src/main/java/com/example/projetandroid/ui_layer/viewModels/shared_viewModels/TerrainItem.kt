package com.example.projetandroid.ui_layer.viewModels.shared_viewModels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import com.example.projetandroid.R
import com.example.projetandroid.model.terrrain.TerrrainModel


@Composable
fun TerrainCard(
    terrainId: TerrrainModel,
    selectedIndex: Int,
    index: Int,
    onClick: (Int) -> Unit, onLocationClick: (() -> Unit)?
) {
    Card(
        onClick = {
            onClick(index)
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Row(Modifier.weight(1f)) {
                Checkbox(checked = selectedIndex == index, onCheckedChange = {})
                Column {
                    Text(text = terrainId.label, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${terrainId.price.toString()}dt",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = {
                onLocationClick?.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = null
                )
            }
        }

    }

}


@Preview(showSystemUi = true)
@Composable
private fun TerrainCardPreview() {

//    TerrainCard(
//        terrainId = TerrrainModel(
//            _id = "fafafa",
//            date = "dafafafa",
//            description = "daaata",
//            width = 30.3,
//            height = 30.4,
//            label = "label",
//            latitude = "fafa",
//            longitude = "fago",
//            managerId = "manager id",
//            price = 40.3,
//            createdAt = "",
//            __v = 0,
//            matchsIn = emptyList(),
//            updatedAt = ""
//        ), index = 4
//    )
}