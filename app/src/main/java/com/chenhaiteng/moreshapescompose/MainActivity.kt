package com.chenhaiteng.moreshapescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chenhaiteng.moreshapes.CustomShapeView
import com.chenhaiteng.moreshapes.KilaWheel
import com.chenhaiteng.moreshapes.Lotus
import com.chenhaiteng.moreshapes.RectangleLotus
import com.chenhaiteng.moreshapescompose.ui.theme.MoreShapesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoreShapesComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        CustomShapeView(Modifier.size(150.dp), Lotus(), color = Color.Red)
        CustomShapeView(Modifier.size(150.dp), KilaWheel(), color = Color.Blue)
        CustomShapeView(Modifier.size(150.dp), RectangleLotus(), color = Color.Green)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoreShapesComposeTheme {
        Greeting("Android", Modifier.fillMaxSize())
    }
}