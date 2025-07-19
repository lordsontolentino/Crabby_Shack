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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crabbyshack.ui.theme.CrabbyShackTheme

class MenuItems : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val orderType = intent.getStringExtra("orderType") ?: "Unknown"

        setContent {
            CrabbyShackTheme {
                MenuLayout()
            }
        }
    }
}

@Preview (widthDp = 393, heightDp = 851)
@Composable
fun MenuLayout()
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
            //Menu Items
            Items()
        }
    }
}

@Composable
fun Items()
{
    var burgerCount by remember { mutableStateOf(0) }
    var friesCount by remember { mutableStateOf(0) }
    var sodaCount by remember { mutableStateOf(0) }
    var iceCreamCount by remember { mutableStateOf(0) }

    // Calculate total
    val total = (burgerCount * 150) +
            (friesCount * 80) +
            (sodaCount * 50) +
            (iceCreamCount * 60)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("You selected: Dine Take", style = MaterialTheme.typography.headlineSmall)
        Text("Select your items:", style = MaterialTheme.typography.titleMedium)

        Row(

        )
        {
            Button(onClick = {  }) {
                Text("Burger\n₱150")
            }

            Button(onClick = { friesCount++ }) {
                Text("Fries\n₱80")
            }
        }

        Row(

        )
        {
            Button(onClick = { sodaCount++ }) {
                Text("Soda\n₱50")
            }

            Button(onClick = { iceCreamCount++ }) {
                Text("Ice Cream\n₱60")
            }
        }
    }
}

//suspend fun for order_items

