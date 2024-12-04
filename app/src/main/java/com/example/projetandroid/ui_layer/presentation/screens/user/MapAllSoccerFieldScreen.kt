package com.example.projetandroid.ui_layer.presentation.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.projetandroid.LatLongSerializable
import com.example.projetandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapAllSoccerFieldScreen(
    listOfSoccerField: List<LatLongSerializable>,
    controller: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    val mapState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(LatLng(latitude, long), 15f)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Position")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        controller.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            GoogleMap(
                cameraPositionState = mapState,
                onMapClick = {
                    println("There we go man $it")
                },
                onMapLoaded = {
                    println("map loaded")

                    listOfSoccerField.getOrNull(0).let {
                        coroutineScope.launch {
                            mapState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(it?.latitude ?: 30.3, it?.longitude ?: 9.3),
                                    7f
                                )
                            )
                        }
                    }
                }
            ) {
                listOfSoccerField.map {
                    Marker(
                        onClick = { marker ->
                            controller.previousBackStackEntry?.savedStateHandle?.set("id", it._id)
                            controller.popBackStack()
                            true
                        },
                        state = MarkerState(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        )
                    )
                }
            }
        }
    }


}