package com.sks.littlelemon.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sks.littlelemon.R
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.views.StepperView

@Composable
fun DishDetails(id: Int) {
    val dish = DishRepository.getDish(id)
    var count by remember { mutableIntStateOf(1) }

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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = dish.imageResource),
            contentDescription = stringResource(dish.nameResourceId),
            modifier = Modifier
                .aspectRatio(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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

            StepperView(
                minStepValue = 1,
                maxStepValue = 10,
                count = count,
                onAdd = {
                    count++
                },
                onRemove = {
                    count--
                }
            )
        }
    }
}

@Preview
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 1)
}
