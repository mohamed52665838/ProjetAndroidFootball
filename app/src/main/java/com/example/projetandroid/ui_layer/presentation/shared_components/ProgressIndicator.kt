package com.example.projetandroid.ui_layer.presentation.shared_components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetandroid.R
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomProgressIndicatorDemo()
        }
    }
}

@Composable
fun CustomProgressIndicatorDemo() {
    var progress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomProgressIndicator(progress = progress, modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                // Animate progress from 0 to 1
                val animation = Animatable(0f)
                animation.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 2000)
                ) {
                    progress = value
                }
            }
        }) {
            Text("Start Progress")
        }
    }
}

@Composable
fun CustomProgressIndicator(progress: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        var canvasSize by remember { mutableStateOf(Size.Zero) }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                canvasSize = Size(it.width.toFloat(), it.height.toFloat())
            }
        ) {
            val strokeWidth = 20f
            val diameter = size.minDimension - strokeWidth
            val topLeftOffset = Offset(strokeWidth / 2, strokeWidth / 2)

            // Draw background circle
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeftOffset,
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth)
            )

            // Draw progress arc
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                topLeft = topLeftOffset,
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth)
            )
        }

        // Calculate the position of the football
        if (canvasSize != Size.Zero) {
            val angle = Math.toRadians((progress * 360 - 90).toDouble())
            val radius = canvasSize.minDimension / 2 - 20
            val center = Offset(canvasSize.width / 2, canvasSize.height / 2)
            val ballCenter = Offset(
                x = (center.x + radius * cos(angle)).toFloat(),
                y = (center.y + radius * sin(angle)).toFloat()
            )

            Image(
                painter = painterResource(id = R.drawable.football), // Replace with your football image
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun CustomProgressIndicatorExample(modifier: Modifier = Modifier) {
    val transitionState = rememberInfiniteTransition(label = "infinite rotation")
    val percentage = transitionState.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500), repeatMode = RepeatMode.Reverse),
        label = "animated value"
    )

    val rotationZ = transitionState.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500), repeatMode = RepeatMode.Reverse),
        label = "animate rotation"

    )

    Image(
        modifier = Modifier
            .graphicsLayer(
                scaleX = percentage.value,
                scaleY = percentage.value,
                rotationZ = rotationZ.value * 360
            )
            .size(100.dp),
        painter = painterResource(id = R.drawable.football),
        contentDescription = null
    )

}

@Composable
fun LayeredBoxDemo() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Bottom Layer: Unresponsive
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Button(
                onClick = { /* This won't trigger */ },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Unresponsive Button")
            }
        }

        // Top Layer: Intercepts all touch events
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent) // Transparent to still see the bottom layer
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExamplePreview() {
    LayeredBoxDemo()
}


//@Composable
//private fun ProgressIndicatorPreview() {
//    CustomProgressIndicatorExample()
//}
