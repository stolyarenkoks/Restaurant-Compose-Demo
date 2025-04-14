package com.sks.littlelemon.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sks.littlelemon.R
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.views.DishView

private const val TAG = "LITTLE_LEMON_LOWER"

@Composable
fun HomeDishesView(navController: NavHostController, dishes: List<Dish> = listOf()) {
    Log.d(TAG, "Rendering LowerPanel with ${dishes.size} dishes")
    Column {
        WeeklySpecialCard()
        LazyColumn {
            itemsIndexed(dishes) { index, dish ->
                Log.d(TAG, "Rendering dish at index $index: ${dish.id}")
                DishView(dish = dish, navController = navController)
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