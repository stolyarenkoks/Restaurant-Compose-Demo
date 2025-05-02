package com.sks.littlelemon.screens.dishDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.destinations.Cart
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.screens.navigation.TopBar
import com.sks.littlelemon.ui.theme.LittleLemonColor
import com.sks.littlelemon.views.StepperView

// MARK: - View

@Composable
fun DishDetailsScreen(
    id: Int,
    navController: NavController,
    viewModel: DishDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(id) {
        viewModel.loadDish(id)
    }

    Scaffold(
        topBar = { 
            TopBar(
                showBackButton = true,
                showCartIcon = false,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        uiState.dish?.let { dish ->
            DishDetailsView(
                dish = dish,
                quantity = uiState.quantity,
                onQuantityChange = viewModel::updateQuantity,
                onAddToCart = {
                    viewModel.addToCart()
                    Toast.makeText(
                        context,
                        "Order added to cart!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

// MARK: - Private View Components

@Composable
private fun DishDetailsView(
    dish: Dish,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                count = quantity,
                onAdd = { onQuantityChange(quantity + 1) },
                onRemove = { onQuantityChange(quantity - 1) }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onAddToCart,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColor.yellow)
            ) {
                Text(
                    text = "Add for $${dish.price * quantity}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LittleLemonColor.green
                )
            }
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DishDetailsViewPreview() {
    val viewModel: DishDetailsViewModel = viewModel()
    DishDetailsScreen(
        id = 1,
        navController = rememberNavController(),
        viewModel = viewModel
    )
}