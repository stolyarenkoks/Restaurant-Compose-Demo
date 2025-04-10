package com.sks.littlelemon.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sks.littlelemon.R
import com.sks.littlelemon.models.Dish
import androidx.compose.ui.text.font.FontWeight
import com.sks.littlelemon.router.Router

private const val TAG = "LITTLE_LEMON_LOWER"

@Composable
fun HomeDishesView(navController: NavHostController, dishes: List<Dish> = listOf()) {
    Log.d(TAG, "Rendering LowerPanel with ${dishes.size} dishes")
    Column {
        WeeklySpecialCard()
        LazyColumn {
            itemsIndexed(dishes) { index, dish ->
                Log.d(TAG, "Rendering dish at index $index: ${dish.id}")
                DishCell(dish = dish, navController = navController)
            }
        }
    }
}

@Composable
private fun WeeklySpecialCard() {
    Log.d(TAG, "Rendering WeeklySpecialCard")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.weekly_special),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
private fun DishCell(dish: Dish, navController: NavHostController) {
    Log.d(TAG, "Rendering DishCell for dish ID: ${dish.id}")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            Log.d(TAG, "Dish clicked with ID: ${dish.id}")
            navController.navigate("${Router.dishDetailsRoute}/${dish.id}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(dish.nameResourceId),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(dish.descriptionResourceId),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(top = 4.dp)
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Image(
                painter = painterResource(id = dish.imageResource),
                contentDescription = stringResource(dish.nameResourceId),
                modifier = Modifier
                    .height(120.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}