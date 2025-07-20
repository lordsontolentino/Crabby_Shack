package com.example.crabbyshack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation.Url
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crabbyshack.ui.theme.CrabbyShackTheme
import kotlinx.coroutines.launch

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.call.body
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.ktor.http.encodeURLParameter


class MenuItems : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val orderType = intent.getStringExtra("orderType") ?: "Unknown"

        setContent {
            CrabbyShackTheme {
                MenuLayout(orderType)
            }
        }
    }

    @Preview (widthDp = 393, heightDp = 851)
    @Composable
    fun MenuLayout(orderType: String = "Dine in")
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
                Items(orderType)
            }
        }
    }

    @Composable
    fun Items(orderType: String)
    {
        val mContext = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        var boxWidth: Int = 175
        var boxHeight: Int = 225
        var textSize: Int = 16

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("You selected: $orderType", style = MaterialTheme.typography.headlineSmall)
            Text("Select your items:", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Button(onClick = {
                    coroutineScope.launch{
                        orders(mContext, "Calamares", "150", orderType)
                    }
                },
                    modifier = Modifier
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp)) {
                    Text(
                        text = "Calamares\n₱150",
                        fontSize = textSize.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Button(onClick = {
                    coroutineScope.launch{
                        orders(mContext, "Tilapia", "100", orderType)
                    }
                },
                    modifier = Modifier
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp)) {
                    Text(
                        text = "Tilapia\n₱100",
                        fontSize = textSize.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Button(onClick = {
                    coroutineScope.launch{
                        orders(mContext, "Daing", "60", orderType)
                    }
                },
                    modifier = Modifier
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp)) {
                    Text("Daing\n₱60")
                }

                Button(onClick = {
                    coroutineScope.launch{
                        orders(mContext, "Kinilaw", "100", orderType)
                    }
                },
                    modifier = Modifier
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(20.dp),
                    shape = RoundedCornerShape(10.dp)) {
                    Text("Kinilaw\n₱100")
                }
            }
        }
    }

    suspend fun orders(context: Context, name:String, price: String, order_type: String)
    {
        val client = HttpClient(CIO)

        val response: HttpResponse = client.get(
//            "http://10.0.2.2:80/CrabbyShack/REST/orders.php?name=Shrimp&price=170&order_type=DineIn"
            "http://10.0.2.2:80/CrabbyShack/REST/orders.php?"
                    +"name=" + name + "&price=" + price + "&order_type=" + order_type
        )
        val stringBody: String = response.body<String>().toString()
        println(response.status.toString())
        println(stringBody)
        println("$name, $price, $order_type")
        Toast.makeText(context, stringBody, Toast.LENGTH_SHORT).show()
        client.close()
    }
}



