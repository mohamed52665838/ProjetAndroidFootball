package com.example.projetandroid.ui_layer.presentation.shared_manager_user.dashboard_composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.projetandroid.ui_layer.viewModels.shared_viewModels.DashboardViewModel


@Composable
fun ActivityComposable(
    dashboardViewModel: DashboardViewModel,
    androidNavController: NavController,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Activities")
    }
}