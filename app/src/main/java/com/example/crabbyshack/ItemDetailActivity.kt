package com.example.crabbyshack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crabbyshack.ui.theme.CrabbyShackTheme

class ItemDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrabbyShackTheme {
                val name = intent.getStringExtra("name") ?: ""
                val price = intent.getIntExtra("price", 0)
                val image = intent.getIntExtra("image", 0)

                ItemDetailScreen(name, price, image)
            }
        }
    }

    @Composable
    fun ItemDetailScreen(name: String, price: Int, image: Int) {
        var quantity by remember { mutableStateOf(1) }
        var mashedPotato by remember { mutableStateOf(0) }
        var coke by remember { mutableStateOf(0) }

        val mashedPrice = 80
        val cokePrice = 50

        val subtotal = (price * quantity) + (mashedPotato * mashedPrice) + (coke * cokePrice)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Top Banner
            Image(
                painter = painterResource(id = R.drawable.seafood_banner),
                contentDescription = "Banner",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                // Centered Logo
                Text(
                    "CRABBY SHACK",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF25C69),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Product info aligned to the left
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = name,
                        modifier = Modifier
                            .size(140.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("₱$price", color = Color(0xFF1985A1), fontSize = 18.sp)
                        Text("15-20 minutes | 320 kcal | 200g", fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Text("-", fontSize = 20.sp)
                            }
                            Text(quantity.toString(), fontSize = 18.sp)
                            IconButton(onClick = { quantity++ }) {
                                Text("+", fontSize = 20.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description aligned to the left
                Text(
                    "Plump tiger prawns grilled and drizzled with herbed garlic butter.",
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.weight(1f)) // Push add-ons to bottom

                // "Add to Order" Centered
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Add to Order", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.mashed_potato),
                                contentDescription = "Mashed Potato",
                                modifier = Modifier.size(50.dp)
                            )
                            Text("Mashed Potato\n₱80", fontSize = 12.sp, textAlign = TextAlign.Center)

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { if (mashedPotato > 0) mashedPotato-- }) {
                                    Text("-", fontSize = 18.sp)
                                }
                                Text(mashedPotato.toString(), fontSize = 14.sp)
                                IconButton(onClick = { mashedPotato++ }) {
                                    Text("+", fontSize = 18.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.coke),
                                contentDescription = "Coca Cola",
                                modifier = Modifier.size(50.dp)
                            )
                            Text("Coca Cola\n₱50", fontSize = 12.sp, textAlign = TextAlign.Center)

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { if (coke > 0) coke-- }) {
                                    Text("-", fontSize = 18.sp)
                                }
                                Text(coke.toString(), fontSize = 14.sp)
                                IconButton(onClick = { coke++ }) {
                                    Text("+", fontSize = 18.sp)
                                }
                            }
                        }
                    }
                }
            }

            BottomBar(subtotal)
        }
    }

    @Composable
    fun BottomBar(subtotal: Int) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF1985A1))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal: ₱$subtotal", color = Color.White, fontSize = 16.sp)

            Button(
                onClick = { /* TODO: Handle Add to Cart */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF25C69)),
                shape = RoundedCornerShape(50)
            ) {
                Text("Add to Cart", color = Color.White)
            }
        }
    }
}
