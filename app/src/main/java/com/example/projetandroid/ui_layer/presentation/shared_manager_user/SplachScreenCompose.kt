package com.example.projetandroid.ui_layer.presentation.shared_manager_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.R
import com.example.projetandroid.ui_layer.presentation.theme.surfaceColor
import kotlinx.coroutines.delay


@Composable
fun SplashScreenCompose(
    modifier: Modifier = Modifier
) {
    LaunchedEffect(true) {
        delay(1000)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.takwira),
                contentDescription = "logo", modifier = Modifier.width(160.dp)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(id = R.drawable.soccer_player),
                contentDescription = "logo",
                modifier = Modifier
                    .clip(RoundedCornerShape(120.dp))
                    .size(260.dp)
            )
        }
    }
}


@Composable
fun SplashScreenComposeP(
) {
    LaunchedEffect(true) {
        delay(1000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize() // width and height
            .background(surfaceColor), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.takwira),
                contentDescription = "logo", modifier = Modifier.width(160.dp)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(id = R.drawable.trophy),
                contentDescription = "logo", modifier = Modifier.size(260.dp)
            )
        }
    }
}


// arrangement   (start, end, space-between, space-around, space-evenly)
// alignment (start, Bottom, center)

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenComposePreview() {
    SplashScreenComposeP()
}


// state (string, int, boolean, object)


@Composable
fun MyCustomButton() {
    Row {
        Button(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                contentDescription = "Hello World"
            )
            Text(text = "Button")
        }
    }
}

// arrangement

// start
// |--                |

// end
// |                --|

// between
// |-                -|

// space around
// |   -     -   |

// space evenly
// | -     - |


// alignment

// |- - - - -|              ---->

// element devided in 2
// visuelle Rext/Button/EditText
// no visuelle layouts

// xml (constraint, linear, relative)
// Column / Row / Box


//







































