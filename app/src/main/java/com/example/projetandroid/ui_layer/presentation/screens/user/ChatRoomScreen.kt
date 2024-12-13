package com.example.projetandroid.ui_layer.presentation.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetandroid.MessagePayload
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.event.SocketWrapperClass
import com.example.projetandroid.ui_layer.presentation.shared_components.AlertDialogAsyncComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.AlertDialogComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.MessageComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.OwnMessageComponent
import com.example.projetandroid.ui_layer.presentation.shared_components.rememberAlertDialogAsyncState
import com.example.projetandroid.ui_layer.presentation.shared_components.show
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.ChatRoomViewModel
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.LoadingDataError
import com.example.projetandroid.ui_layer.viewModels.user_viewModels.ServerStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    charRoomViewModel: ChatRoomViewModel,
    dashboardViewModel: DashboardViewModel,
    navController: NavController
) {

    val listOfMessages = charRoomViewModel.listOfMessagePayload
    val isMessagesLoading = charRoomViewModel.matchesLoading
    val alertDialogAsyncState = rememberAlertDialogAsyncState()
    val connectionStatus = charRoomViewModel.serverStatus
    val lazyListState = charRoomViewModel.lazyListState
    var messageData = charRoomViewModel.messageData

    LaunchedEffect(key1 = Unit) {
        charRoomViewModel.errorChannel.onEach { errorMessage ->
            errorMessage?.let { errorMessageNotNull ->
                alertDialogAsyncState.show(
                    AlertDialogComponent(
                        errorMessageNotNull.onDismissError,
                        content = {
                            Text(text = errorMessageNotNull.errorMessage)
                        }
                    )
                )
            }
        }.launchIn(scope = this)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    AlertDialogAsyncComponent(alertDialogAsyncState)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Room") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "back"
                    )
                }
            },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.padding(
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(it)
        ) {
            if (!isMessagesLoading.value)
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    items(items = listOfMessages) {
                        if (dashboardViewModel.isMineById(it.userId))
                            OwnMessageComponent(content = it.content, time = it.datetime_)
                        else
                            MessageComponent(
                                userName = it.userName,
                                content = it.content,
                                time = it.datetime_
                            )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            else
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            Row(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(
                        bottom =
                        WindowInsets.ime
                            .asPaddingValues()
                            .calculateTopPadding()
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = messageData,
                    onValueChange = charRoomViewModel::onMessageDataChange,
                    modifier = Modifier
                        .weight(1f),
                    placeholder = {
                        Text(text = "message ...")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                charRoomViewModel.sendMessage()
                            },
                            enabled = connectionStatus.value !is ServerStatus.ConnectingError

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_send_24),
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            if (connectionStatus.value is ServerStatus.ConnectingError) {
                Card(
                    onClick = { /*TODO*/ },
                    colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.4f))
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        Text(
                            text = "failed to connect",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ChatRoomScreenPreview() {
}
