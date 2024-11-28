package com.example.projetandroid.ui_layer.presentation.screens.shared_manager_user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.packInts
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapOfMatch(
    latitude: Double,
    long: Double,
    navController: NavController
) {


    SideEffect {
        println(latitude)
        println(long)
    }
    val coroutineScope = rememberCoroutineScope()
    val mapState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, long), 15f)
    }
    val markerState = rememberMarkerState(position = LatLng(latitude, long))
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Position")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
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
                    coroutineScope.launch {
                        mapState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(latitude, long),
                                15f
                            )
                        )
                    }
                    markerState.position =
                        LatLng(latitude, long)
                }
            ) {
                Marker(
                    state = markerState,
                    title = "terrain position"
                )
            }
        }
    }


}