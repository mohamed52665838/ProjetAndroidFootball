package com.example.projetandroid.ui_layer.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projetandroid.AddSoccerField
import com.example.projetandroid.Dismiss
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme


@Composable
fun HandleUIEvents(
    modifier: Modifier = Modifier,
    uiState: UiState,
    navController: NavController,
    onDone: (() -> Unit)? = null,
) {


    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (uiState) {
            is UiState.Error -> {
                AlertDialog(onDismissRequest = { onDone?.invoke() }, confirmButton = {
                    TextButton(onClick = {
                        uiState.dismiss?.let {
                            when (it) {
                                is Dismiss.DismissState -> {
                                    it.dismiss.invoke()
                                }

                                is Dismiss.DismissStateWithNavigation -> {
                                    it.dismiss.invoke(navController)
                                }
                            }
                        }
                    }) {
                        Text(text = "OK", style = MaterialTheme.typography.bodyMedium)
                    }
                },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_error_outline_24),
                                contentDescription = "hello world",
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                )
            }

            is UiState.Loading -> {
//                AlertDialog(onDismissRequest = { onDone?.invoke() },
//                    confirmButton = {},
//                    text = {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Text(text = uiState.message ?: "Loading...")
//                            Spacer(modifier = Modifier.height(16.dp))
//                            Box(
//                                contentAlignment = Alignment.Center,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                CustomProgressIndicatorExample()
//                            }
//                        }
//                    }
//                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.White.copy(alpha = 0.6f))
                        .pointerInput(Unit) {} // Consumes all touch gestures
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    CustomProgressIndicatorExample()
                }
            }

            is UiState.Success -> {
                AlertDialog(onDismissRequest = {
                    uiState.dismiss?.let {
                        when (it) {
                            is Dismiss.DismissState -> {
                                it.dismiss.invoke()
                            }

                            is Dismiss.DismissStateWithNavigation -> {
                                it.dismiss.invoke(navController)
                            }
                        }
                    }
                },
                    confirmButton = {
                        TextButton(onClick = {
                            uiState.dismiss?.let {
                                when (it) {
                                    is Dismiss.DismissState -> {
                                        it.dismiss.invoke()
                                    }

                                    is Dismiss.DismissStateWithNavigation -> {
                                        it.dismiss.invoke(navController)
                                    }

                                }
                            }
                        }) {
                            Text(text = "OK", style = MaterialTheme.typography.bodyMedium)
                        }
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_check_circle_24),
                                contentDescription = "hello world",
                                tint = Color.Green
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                )
            }

            is UiState.NavigateEvent -> {
                val navigation = uiState.navigation
                navigation(navController)
            }

            is UiState.Idle -> {
                // empty
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ShowExamples() {

    val uiState = UiState.Success(message = "Big Success")
    ProjetAndroidTheme {
        HandleUIEvents(uiState = uiState, navController = rememberNavController())
    }
}