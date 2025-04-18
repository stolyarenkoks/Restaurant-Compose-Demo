package com.sks.littlelemon.screens.dishDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.R
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.screens.navigation.TopBar
import com.sks.littlelemon.views.StepperView

// MARK: - View

@Composable
fun DishDetailsScreen(
    id: Int,
    navController: NavController
) {
    val dish = DishRepository.getDish(id)

    if (dish != null) {
        Scaffold(
            topBar = { 
                TopBar(
                    showBackButton = true,
                    onBackClick = { navController.navigateUp() }
                )
            }
        ) { paddingValues ->
            DishDetailsView(
                dish = dish,
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

// MARK: - Private View Components

@Composable
private fun DishDetailsView(
    dish: Dish,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var count by remember { mutableIntStateOf(1) }
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = dish.imageResource),
            contentDescription = stringResource(dish.nameResourceId),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
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

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(
                        context,
                        "Order added to cart!",
                        Toast.LENGTH_SHORT)
                        .show()
                    navController.navigateUp()
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
            ) {
                Text(
                    text = "Add for $${dish.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginViewPreview() {
    DishDetailsView(
        dish = DishRepository.getDish(id = 1)!!,
        navController = rememberNavController()
    )
}