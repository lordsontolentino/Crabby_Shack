package com.example.crabbyshack

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crabbyshack.ui.theme.CrabbyShackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrabbyShackTheme {
                MainLayout()
            }
        }
    }
}

@Preview (widthDp = 393, heightDp = 851)
@Composable
fun MainLayout()
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFcef9ff)),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            MainMenu()
        }
    }
}

@Composable
fun MainMenu() //Dine in or Take out
{
    val context = LocalContext.current

    var boxWidth: Int = 175
    var boxHeight: Int = 225
    var textSize: Int = 20

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Welcome to Crabby Shack!",
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                onClick = {
                    //Code for Dine-in to be passed using Intent
                    val intent = Intent(context, MenuItems::class.java)
                    intent.putExtra("orderType", "DineIn")
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .width(boxWidth.dp)
                    .height(boxHeight.dp)
                    .padding(20.dp),

                shape = RoundedCornerShape(10.dp)
            )
            {
                Text(
                    text = "Dine in",
                    fontSize = textSize.sp
                )
            }

            Button(
                onClick = {
                    //Code for Takeout to be passed using Intent
                    val intent = Intent(context, MenuItems::class.java)
                    intent.putExtra("orderType", "Takeout")
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .width(boxWidth.dp)
                    .height(boxHeight.dp)
                    .padding(20.dp),

                shape = RoundedCornerShape(10.dp)
            )
            {
                Text(
                    text = "Take out",
                    fontSize = textSize.sp
                )
            }
        }
    }
}