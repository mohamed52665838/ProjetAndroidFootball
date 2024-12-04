package com.example.projetandroid.ui_layer.presentation.screens.user

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetandroid.LatLongSerializable
import com.example.projetandroid.ListSoccerFieldScreenMap
import com.example.projetandroid.MapScreen
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.MapOfMatch
import com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user.dashboard_composables.TerrainItem
import com.example.projetandroid.ui_layer.presentation.shared_components.DropDownOfTerrains
import com.example.projetandroid.ui_layer.presentation.shared_components.HandleUIEvents
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.TerrainCard
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.AddNowMatchViewModel
import java.time.LocalDate
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNowMatchComposable(
    addNowMatchViewModel: AddNowMatchViewModel,
    navController: NavController
) {

    val localcontextasactivity = LocalContext.current as Activity

    var selectedIndex = addNowMatchViewModel.selectedIndex
    val datePickerDialog = DatePickerDialog(
        localcontextasactivity,
        { _, year, month, dayOfMonth ->

            val date = LocalDate.of(year, month, dayOfMonth)
            addNowMatchViewModel.date = date
        },
        2023,
        10,
        11
    )


    navController.currentBackStackEntry?.savedStateHandle?.get<String>("id")?.let { id_ ->
        println("we've got an id $id_")
        addNowMatchViewModel.selectedIndex =
            addNowMatchViewModel.terrrainModel.value?.indexOfFirst {
                it._id == id_
            } ?: -1

    } ?: run {
        println("no id provided")
    }


    val timePicker = TimePickerDialog(
        localcontextasactivity,
        { _, hour, minute ->
            val time = LocalTime.of(hour, minute)
            addNowMatchViewModel.time = time
        },
        0,
        0,
        true
    )

    val uiState = addNowMatchViewModel.uiState.collectAsState(initial = UiState.Idle)
    val listOfTerrain = addNowMatchViewModel.terrrainModel.value


    HandleUIEvents(
        uiState = uiState.value,
        navController = navController,
        onDone = addNowMatchViewModel::restUiState
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Match")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        addNowMatchViewModel.addMatch()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_check_24),
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {
                        addNowMatchViewModel.terrrainModel.value?.let { listOfTerrain ->
                            val listValues = listOfTerrain.map {
                                LatLongSerializable(
                                    it.latitude.toDouble(),
                                    it.longitude.toDouble(),
                                    it._id
                                )
                            }
                            navController.navigate(ListSoccerFieldScreenMap(listValues))
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_language_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Date")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        datePickerDialog.show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = "date"
                        )
                    }
                    Text(text = addNowMatchViewModel.date?.toString() ?: "")
                }
                Text(text = "Time")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        timePicker.show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = "date"
                        )
                    }
                    Text(text = addNowMatchViewModel.time?.toString() ?: "")
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Soccer Fields Exists", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                listOfTerrain?.let {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        itemsIndexed(it) { index, card ->
                            TerrainCard(
                                terrainId = card,
                                index = index,
                                selectedIndex = selectedIndex,
                                onClick = { index ->
                                    addNowMatchViewModel.selectedIndex = index
                                    println("selected index $index")
                                },
                                onLocationClick = {
                                    navController.navigate(
                                        MapScreen(
                                            card.latitude.toDouble(),
                                            card.longitude.toDouble()
                                        )
                                    )
                                }

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AddNowMatchComposablePreview() {
    ProjetAndroidTheme {
    }
}
