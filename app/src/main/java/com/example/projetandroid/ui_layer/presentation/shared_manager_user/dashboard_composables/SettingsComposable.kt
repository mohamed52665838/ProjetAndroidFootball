package com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projetandroid.Profile
import com.example.projetandroid.R
import com.example.projetandroid.SignIn
import com.example.projetandroid.ui_layer.presentation.theme.ProjetAndroidTheme
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel
import java.util.Stack


// TODO("we need to add soccer field for the manager")
// this one is common between user & manager but one more thing that we need settings of soccer field in the manager
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsComposable(
    navController: NavController,
    dashboardViewModel: DashboardViewModel,
    onSignOut: () -> Unit
) {
    data class MenuItem(
        val text: String,
        val iconId: Int,
        val callback: () -> Unit = {}
    )


    // begin of screen state

    var isShowDisconnectAlert by rememberSaveable {
        mutableStateOf(false)
    }


    // end screen state


    val listOfMenu = listOf(
        MenuItem(
            text = "Profile",
            iconId = R.drawable.baseline_person_outline_24,
            callback = { navController.navigate(Profile) }),
        MenuItem(text = "Guide", iconId = R.drawable.guide),
        MenuItem(text = "Licence", iconId = R.drawable.licence),
        MenuItem(text = "Policy and Privacy", iconId = R.drawable.policy),
        MenuItem(
            text = "Disconnect",
            iconId = R.drawable.exit,
            callback = {
                onSignOut()
                navController.navigate(SignIn) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }),
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Settings", style = MaterialTheme.typography.titleLarge)
            }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            Column(Modifier.padding(top = 24.dp)) {
                listOfMenu.map {
                    MenuItemComposable(text = it.text, iconId = it.iconId, onClick = it.callback)
                }
            }
        }
    }
}


@Composable
fun MenuItemComposable(
    text: String,
    iconId: Int,
    onClick: () -> Unit = {}
) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.Transparent), onClick = onClick) {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "menu item",
                    modifier = Modifier.size(28.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.height(64.dp)
            ) {
                Text(text = text)
                HorizontalDivider()
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ProfileComposablePreview() {

    ProjetAndroidTheme {
        //    SettingsComposable()
    }


}