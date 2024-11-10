package com.example.projetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import com.example.projetandroid.presentation.LoginComposable
import com.example.projetandroid.ui.theme.ProjetAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        Modifier
                            .padding(innerPadding)
                    ) {
                        LoginComposable()
                        Box(Modifier.windowInsetsPadding(WindowInsets.ime)) {}
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
            .padding(16.dp)
            .background(Color.Red)
            .padding(16.dp)
            .background(Color.Blue)
            .padding(16.dp)
    )
}


// signup
// username / password submit
// receive otp code
// otp code && send with email
// login
// dashboard


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {

    ProjetAndroidTheme {
        Greeting("Android")
    }
}

// row / column