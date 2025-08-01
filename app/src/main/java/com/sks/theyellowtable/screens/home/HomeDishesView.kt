package com.sks.theyellowtable.screens.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sks.theyellowtable.R
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.models.mock
import com.sks.theyellowtable.views.DishView

// MARK: - View

@Composable
fun HomeDishesView(
    dishes: List<Dish> = listOf(),
    onDishSelected: (Int) -> Unit = {},
) {
    Column {
        WeeklySpecialCard()
        LazyColumn {
            itemsIndexed(dishes) { _, dish ->
                DishView(
                    dish = dish,
                    onClick =  {
                        onDishSelected(dish.id)
                    }
                )
            }
        }
    }
}

// MARK: - Private View Components

@Composable
private fun WeeklySpecialCard() {
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

// MARK: - Preview

@Preview
@Composable
private fun HomeDishesPreview() {
    HomeDishesView(dishes = listOf(Dish.mock()))
}