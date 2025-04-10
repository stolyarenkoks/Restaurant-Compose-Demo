package com.sks.littlelemon.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sks.littlelemon.R
import com.sks.littlelemon.repository.DishRepository

@Composable
fun DishDetails(id: Int) {
    val dish = DishRepository.getDish(id)
    
    if (dish == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.dish_not_found),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(dish.nameResourceId),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(dish.descriptionResourceId),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$${dish.price}",
            style = MaterialTheme.typography.titleLarge
        )
        Counter()
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                if (counter > 1) {
                    counter--
                }
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 1)
}
