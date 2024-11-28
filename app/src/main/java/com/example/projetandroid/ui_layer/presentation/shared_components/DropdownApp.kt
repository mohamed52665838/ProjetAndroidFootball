package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.R
import com.example.projetandroid.model.terrrain.TerrrainModel

@Composable
fun DropdownComposableApp(
    value: String,
    onChange: (value: String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {

    var isExpended by remember {
        mutableStateOf(false)
    }


    Box(Modifier.clickable { isExpended = true }) {
        Column(
            Modifier.clickable { isExpended = true }

        ) {
            TextField(
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = value,
                onValueChange = { onChange(it) },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpended = true },
                trailingIcon = {
                    AnimatedContent(targetState = isExpended, label = "dropdown up") { isShow ->
                        if (isShow) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                                contentDescription = "up"
                            )
                        }
                        // change if else :)
                        if (!isShow) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                contentDescription = "up"
                            )
                        }
                    }
                }

            )
            DropdownMenu(
                expanded = isExpended, onDismissRequest = {
                    isExpended = false
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                options.map {
                    HorizontalDivider()
                    DropdownMenuItem(text = {
                        Text(it, style = MaterialTheme.typography.titleMedium)
                    }, onClick = { isExpended = false; onChange(it) }
                    )
                }
            }
        }
    }
}


@Composable
fun DropDownOfTerrains(
    value: TerrrainModel?,
    onChange: (value: TerrrainModel) -> Unit,
    options: List<TerrrainModel>,
    modifier: Modifier = Modifier
) {

    var isExpended by remember {
        mutableStateOf(false)
    }


    Box(Modifier.clickable { isExpended = !isExpended }) {
        Column(
            Modifier.clickable { isExpended = !isExpended }

        ) {
            TextField(
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = value?.label ?: "",
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpended = !isExpended },
                trailingIcon = {
                    AnimatedContent(targetState = isExpended, label = "dropdown up") { isShow ->
                        if (isShow) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                                contentDescription = "up"
                            )
                        }
                        // change if else :)
                        if (!isShow) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                contentDescription = "up"
                            )
                        }
                    }
                }

            )
            DropdownMenu(
                expanded = isExpended, onDismissRequest = {
                    isExpended = false
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                options.map {
                    HorizontalDivider()
                    DropdownMenuItem(text = {
                        Text(it.label, style = MaterialTheme.typography.titleMedium)
                    }, onClick = { isExpended = false; onChange(it) }
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DropdownComponentAppPreview() {

    var isExpended by remember {
        mutableStateOf(false)
    }
    val listOfOptions = listOf("option 1", "option 2", "option 3")

    var currentValue by remember {
        mutableStateOf("value")
    }

    val options = listOf("first option", "second option", "third option")


    val onChange = { option: String ->
        println("here we option $option is selected")
        currentValue = option
    }


}


@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select an option") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Button to open the dropdown
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selectedOption)
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Option 1", "Option 2", "Option 3").forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}

