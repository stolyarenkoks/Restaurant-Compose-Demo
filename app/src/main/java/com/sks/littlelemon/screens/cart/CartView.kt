package com.sks.littlelemon.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.models.CartItem
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.views.TopBar
import com.sks.littlelemon.ui.theme.LittleLemonColor
import com.sks.littlelemon.views.DishView
import com.sks.littlelemon.views.StepperView

// MARK: - View

@Composable
fun CartView(
    navController: NavController,
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                showBackButton = true,
                showCartIcon = false,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartView()
            } else {
                CartView(
                    cartItems = cartItems,
                    totalPrice = viewModel.totalPrice,
                    onRemoveItem = viewModel::removeItem,
                    onUpdateQuantity = viewModel::updateQuantity,
                    onClearCart = viewModel::clearCart
                )
            }
        }
    }
}

// MARK: - Private View Components

@Composable
private fun EmptyCartView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun CartView(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onRemoveItem: (Int) -> Unit,
    onUpdateQuantity: (Int, Int) -> Unit,
    onClearCart: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartItems) { item ->
                CartItemCard(
                    cartItem = item,
                    onRemove = { onRemoveItem(item.dish.id) },
                    onQuantityChange = { newQuantity ->
                        onUpdateQuantity(
                            item.dish.id,
                            newQuantity
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total:", style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onClearCart,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColor.yellow)
            ) {
                Text(
                    text = "Clear Cart", color = LittleLemonColor.green
                )
            }
        }
    }
}

@Composable
private fun CartItemCard(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            DishView(dish = cartItem.dish)

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                StepperView(
                    minStepValue = 1,
                    maxStepValue = 10,
                    count = cartItem.quantity,
                    onAdd = { onQuantityChange(cartItem.quantity + 1) },
                    onRemove = { onQuantityChange(cartItem.quantity - 1) }
                )

                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Remove item"
                    )
                }
            }
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CartPreview() {
    val viewModel: CartViewModel = viewModel()
    viewModel.addToCart(DishRepository.dishes[0], 2)
    viewModel.addToCart(DishRepository.dishes[2], 1)

    CartView(
        navController = rememberNavController(), viewModel = viewModel
    )
}

@Preview(name = "Empty Cart", showBackground = true, showSystemUi = true)
@Composable
private fun EmptyCartPreview() {
    val viewModel: CartViewModel = viewModel()
    viewModel.clearCart()

    CartView(
        navController = rememberNavController(), viewModel = viewModel
    )
}