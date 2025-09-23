package com.sks.theyellowtable.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.theyellowtable.R
import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.views.TopBar
import com.sks.theyellowtable.ui.theme.TheYellowTableColor
import com.sks.theyellowtable.views.DishView
import com.sks.theyellowtable.views.StepperView
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.models.mock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState

// MARK: - View

@Composable
fun CartView(
    navController: NavController,
    viewModel: CartViewModel = koinViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    CartView(
        navController = navController,
        cartItems = cartItems,
        totalPrice = viewModel.totalPrice,
        onRemoveItem = viewModel::removeItem,
        onUpdateQuantity = viewModel::updateQuantity,
        onClearCart = viewModel::clearCart
    )
}

@Composable
private fun CartView(
    navController: NavController,
    cartItems: List<CartItem>,
    totalPrice: Double,
    onRemoveItem: (Int) -> Unit,
    onUpdateQuantity: (Int, Int) -> Unit,
    onClearCart: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                showBackButton = true,
                showCartIcon = false,
                onBackClick = navController::navigateUp
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
                CartContentView(
                    cartItems = cartItems,
                    totalPrice = totalPrice,
                    onRemoveItem = onRemoveItem,
                    onUpdateQuantity = onUpdateQuantity,
                    onClearCart = onClearCart
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
            text = stringResource(R.string.cart_empty),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
private fun CartContentView(
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
            items(
                items = cartItems,
                key = { it.dish.id }
            ) { item ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                            onRemoveItem(item.dish.id)
                        }
                        true
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.remove_item)
                            )
                        }
                    }
                ) {
                    CartItemCard(
                        cartItem = item,
                        onRemove = { onRemoveItem(item.dish.id) },
                        onQuantityChange = { onUpdateQuantity(item.dish.id, it) }
                    )
                }
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
                    text = stringResource(R.string.total),
                    style = MaterialTheme.typography.titleLarge
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
                colors = ButtonDefaults.buttonColors(containerColor = TheYellowTableColor.yellow)
            ) {
                Text(
                    text = stringResource(R.string.clear_cart),
                    color = TheYellowTableColor.green
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
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.remove_item)
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
    val mockCartItems = listOf(
        CartItem.mock(),
        CartItem.mock(
            dish = Dish.mock(
                2,
                com.sks.theyellowtable.R.string.pasta,
                com.sks.theyellowtable.R.string.pasta_description,
                15.50,
                com.sks.theyellowtable.R.drawable.pasta
            ),
            quantity = 2
        )
    )
    
    CartView(
        navController = rememberNavController(),
        cartItems = mockCartItems,
        totalPrice = 41.48,
        onRemoveItem = {},
        onUpdateQuantity = { _, _ -> },
        onClearCart = {}
    )
}

@Preview(name = "Empty Cart", showBackground = true, showSystemUi = true)
@Composable
private fun EmptyCartPreview() {
    CartView(
        navController = rememberNavController(),
        cartItems = emptyList(),
        totalPrice = 0.0,
        onRemoveItem = {},
        onUpdateQuantity = { _, _ -> },
        onClearCart = {}
    )
}