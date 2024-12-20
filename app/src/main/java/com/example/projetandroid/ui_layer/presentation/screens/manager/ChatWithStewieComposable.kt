package com.example.projetandroid.ui_layer.presentation.screens.manager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.R
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.network.RetrofitInstance
import com.example.projetandroid.data_layer.repository.ChatWithStewieRepository
import com.example.projetandroid.ui_layer.presentation.theme.AppColors
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.ChatWithStewieViewModel
import com.google.android.gms.common.util.DataUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatWithStewieComposable(
    viewModel: ChatWithStewieViewModel = hiltViewModel(),
    navController: NavController
) {

    val listOfMessages = viewModel.listOfMessages
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Idle)
    
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (viewModel.lazyListState.canScrollBackward) Color.Gray.copy(
                        alpha = 0.1f
                    ) else Color.White
                ),
                title = {
                    Text(text = "Stewie")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set("key", 24)
                        navController.popBackStack()

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
                .padding(
                    bottom = WindowInsets.ime
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
        ) {
            Column {
                if (uiState.value is UiState.Error) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (uiState.value as UiState.Error).message,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(onClick = { /*TODO*/ }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_error_outline_24),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "refresh",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(Modifier.weight(1f)) {
                        if (listOfMessages.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.2f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "How can I help You to day")
                            }
                        } else {
                            LazyColumn(
                                state = viewModel.lazyListState
                            ) {
                                items(listOfMessages) {
                                    if (it.isItMine) {
                                        OwnMessageWithStewie(
                                            content = it.content,
                                            time = it.time
                                        )
                                    } else {
                                        StewieMessageComposable(
                                            content = it.content,
                                            time = it.time
                                        )
                                    }
                                }
                                if (uiState.value is UiState.Loading) {
                                    item {
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ai),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    "Stewie",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    maxLines = 4,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            Text(text = "thinking...")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.messageContent,
                    onValueChange = viewModel::onMessageChange,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.sendMessage()
                                viewModel.clearText()
                            },
                            enabled = !(uiState.value is UiState.Loading || uiState.value is UiState.Error)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_send_24),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
private fun ChatWithStewiePreview() {
    ProjetAndroidTheme {
    }
}


@Composable
fun StewieMessageComposable(
    modifier: Modifier = Modifier,
    content: String,
    time: String
) {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    ) {
        Column(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ai),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Stewie",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(text = time, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun OwnMessageWithStewie(
    modifier: Modifier = Modifier,
    content: String,
    time: String
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Bone["DEFAULT"]!!,
        ),
        onClick = { /*TODO*/ },

        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    ) {
        Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(text = time, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun StewieMessage() {
    ProjetAndroidTheme {
        Column {
            StewieMessageComposable(content = "hello there", time = "12:20")
            OwnMessageWithStewie(content = "hello there", time = "13:05")
        }
    }
}






