package com.example.crabbyshack

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crabbyshack.ui.theme.CrabbyShackTheme
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch

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

    @Composable
    fun MenuLayout(orderType: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.seafood_banner),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                )

                Text(
                    text = "CRABBY SHACK",
                    color = Color(0xFFF25C69),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    // Sidebar Menu
                    Column(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(top = 40.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val categories = listOf(
                            R.drawable.ic_main_dish to "Main",
                            R.drawable.ic_salads to "Salads",
                            R.drawable.ic_drinks to "Drinks",
                            R.drawable.ic_desserts to "Desserts",
                            R.drawable.ic_sides to "Sides"
                        )
                        categories.forEach { (icon, label) ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Image(
                                        painter = painterResource(id = icon),
                                        contentDescription = label,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = label,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Main Dishes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        ItemsGrid(orderType)
                    }
                }
            }

            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    @Composable
    fun ItemsGrid(orderType: String) {
        val mContext = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val items = listOf(
            Triple("Calamares", 150, R.drawable.calamares),
            Triple("Tilapia", 100, R.drawable.tilapia),
            Triple("Daing", 60, R.drawable.daing),
            Triple("Kinilaw", 100, R.drawable.kinilaw)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
        ) {
            items(items) { (name, price, image) ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.85f)
                        .clickable {
                            val intent = Intent(mContext, ItemDetailActivity::class.java).apply {
                                putExtra("name", name)
                                putExtra("price", price)
                                putExtra("image", image)
                                putExtra("orderType", orderType)
                            }
                            mContext.startActivity(intent)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "₱$price",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1985A1),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BottomBar(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF1985A1))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal", color = Color.White, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("₱0.00", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF25C69))
                ) {
                    Text("VIEW MY CART", color = Color.White)
                }
            }
        }
    }

    suspend fun orders(context: Context, name: String, price: String, order_type: String) {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(
            "http://10.0.2.2:80/CrabbyShack/REST/orders.php?name=$name&price=$price&order_type=$order_type"
        )
        val stringBody: String = response.body()
        Toast.makeText(context, stringBody, Toast.LENGTH_SHORT).show()
        client.close()
    }
}
