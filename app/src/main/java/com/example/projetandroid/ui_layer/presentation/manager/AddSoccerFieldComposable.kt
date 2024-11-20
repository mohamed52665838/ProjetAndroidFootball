package com.example.projetandroid.ui_layer.presentation.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.AddSoccerField
import com.example.projetandroid.BundledTextField
import com.example.projetandroid.R
import com.example.projetandroid.SoccerFieldSubmissionFragments
import com.example.projetandroid.UiState
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.ui_layer.shared_components.PrimaryButton
import com.example.projetandroid.ui_layer.shared_components.SecondaryButton
import com.example.projetandroid.ui_layer.ui.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelImp
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelPreview
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSoccerFieldComposable(
    viewModel: AddSoccerFieldViewModelImp = hiltViewModel(),
    navController: NavController
) {

    val activity = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)


    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> { // Precise location access granted.
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            viewModel.changeLatLong(LatLng(location.latitude, location.longitude))
                            println("we have the permission and the location is ${location.latitude} ${location.longitude}")

                        }
                    }
                }

                it.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            viewModel.changeLatLong(LatLng(location.latitude, location.longitude))
                            println("we have the permission and the location is ${location.latitude} ${location.longitude}")
                        }
                    }
                }

                else -> {
                    // No location access granted.
                    println("access deny")
                }
            }

        }

    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
    }

    val sharedFlow = viewModel.sharedFlow.collectAsState(initial = UiState.Idle)

    val value = BundledTextField(
        value = remember {
            mutableStateOf("")
        }
    )
    val mapState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.mapLangLat.value, 5f)
    }
    val markerState = rememberMarkerState(position = viewModel.mapLangLat.value)

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Soccer Field")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "return back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        permissionLauncher.launch(
                            listOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ).toTypedArray()
                        )
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                println("this is your location ${location.latitude} ${location.longitude}")
                                viewModel.changeLatLong(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                                markerState.position = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                                coroutineScope.launch {
                                    mapState.animate(
                                        CameraUpdateFactory.newLatLngZoom(
                                            LatLng(
                                                location.latitude,
                                                location.longitude,
                                            ),
                                            15f
                                        )
                                    )
                                }
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_my_location_24),
                            contentDescription = "my location"
                        )
                    }
                }
            )
        },
    ) {
        Box(Modifier.padding(it)) {
            AnimatedContent(
                targetState = viewModel.soccerFieldSubmissionFragment.value,
                label = "submission"
            ) { currentFragment ->
                Box {
                    when (currentFragment) {
                        SoccerFieldSubmissionFragments.FORM_FIRST_FRAGMENT -> {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .verticalScroll(rememberScrollState())
                                    .padding(horizontal = 16.dp)
                            ) {
                                viewModel.listViewModel.map { textField ->
                                    OutlinedTextField(
                                        label = { Text(text = textField.label ?: "unspecified") },
                                        value = textField.value.value,
                                        leadingIcon = {
                                            textField.iconId?.let {
                                                Icon(
                                                    painter = painterResource(id = it),
                                                    contentDescription = "icon",
                                                    modifier = Modifier.height(24.dp)
                                                )
                                            }
                                        },
                                        keyboardOptions = textField.keyboardOptions
                                            ?: KeyboardOptions(keyboardType = KeyboardType.Text),
                                        onValueChange = textField.onChange,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    textField.onErrorMessage?.value?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                PrimaryButton(onClick = {
                                    viewModel.passSecondFragment()
                                }, modifier = Modifier.fillMaxWidth()) {
                                    Text("validate", style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                SecondaryButton(onClick = {
                                }, modifier = Modifier.fillMaxWidth()) {
                                    Text("cancel", style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }

                        SoccerFieldSubmissionFragments.MAP_SECOND_FRAGMENT -> {
                            var queryState by remember {
                                mutableStateOf("")
                            }
                            Column(Modifier.padding(16.dp)) {
                                Column(Modifier.weight(1f)) {
//                                OutlinedTextField(
//                                    placeholder = { Text(text = "search, city") },
//                                    value = queryState, onValueChange = {
//                                        queryState = it
//                                        viewModel.lookupAddress(it)
//                                    },
//                                    modifier = Modifier.fillMaxWidth()
//                                )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        Box(
                                            Modifier
                                                .fillMaxSize()
                                                .background(Color.Transparent)
                                        ) {

                                            GoogleMap(
                                                cameraPositionState = mapState,
                                                onMapClick = {
                                                    println("clicked $it")
                                                    coroutineScope.launch {
                                                        mapState.animate(CameraUpdateFactory.zoomIn())
                                                    }
                                                },
                                                onMapLongClick = {
                                                    println("clicked $it")
                                                    coroutineScope.launch {
                                                        mapState.animate(
                                                            update = CameraUpdateFactory.newLatLng(
                                                                it
                                                            )
                                                        )
                                                        markerState.position = it
                                                        viewModel.changeLatLong(it)
                                                    }
                                                }
                                            ) {
                                                Marker(markerState)
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                PrimaryButton(onClick = {
                                    viewModel.submitSoccerField()
                                }, modifier = Modifier.fillMaxWidth()) {
                                    Text("submit", style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                SecondaryButton(onClick = {
                                    viewModel.backFirstForm()
                                }, modifier = Modifier.fillMaxWidth()) {
                                    Text("cancel", style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }

                    }
                    when (sharedFlow.value) {
                        is UiState.Error -> {
                            AlertDialog(onDismissRequest = { viewModel.resetSharedFlow() },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModel.resetSharedFlow()
                                    }) {
                                        Text(text = "OK")
                                    }
                                },
                                text = {
                                    Text(text = (sharedFlow.value as UiState.Error).message)
                                }
                            )
                        }

                        is UiState.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Success -> {

                            AlertDialog(onDismissRequest = {
                                viewModel.resetSharedFlow()
                                navController.popBackStack()
                            },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModel.resetSharedFlow()
                                        navController.popBackStack()
                                    }) {
                                        Text(text = "OK")
                                    }
                                },
                                text = {
                                    Text(text = (sharedFlow.value as UiState.Success).message)
                                }
                            )

                        }

                        is UiState.Idle -> {
                            // empty
                        }
                    }

                }

            }
        }

    }
}


@Preview(device = Devices.PIXEL_2)
@Composable
private fun MainPreview() {
    ProjetAndroidTheme {
    }
}
