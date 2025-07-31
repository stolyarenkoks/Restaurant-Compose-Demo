package com.sks.littlelemon.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.R
import com.sks.littlelemon.database.MenuItemRoom
import com.sks.littlelemon.views.TopBar

// MARK: - View

@Composable
fun MenuView(
    navController: NavController,
    viewModel: MenuViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                showBackButton = true,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        val databaseMenuItems by viewModel.menuItems.observeAsState(emptyList())
        var orderMenuItems by remember { mutableStateOf(false) }
        var searchPhrase by remember { mutableStateOf("") }

        val menuItems = if (orderMenuItems) {
            databaseMenuItems.sortedBy { it.title }
        } else {
            databaseMenuItems
        }

        val filteredMenuItems = if (searchPhrase.isNotEmpty()) {
            menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        } else {
            menuItems
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_horizontal),
                contentDescription = "logo",
                modifier = Modifier.padding(50.dp)
            )

            Button(
                onClick = { orderMenuItems = !orderMenuItems },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
            ) {
                Text(if (orderMenuItems) "Original Order" else "Order By Name")
            }

            OutlinedTextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
            )

            MenuItemsList(items = filteredMenuItems)
        }
    }
}

@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { menuItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = menuItem.title,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Right,
                    text = "%.2f".format(menuItem.price)
                )
            }
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MenuPreview() {
    // Используем обычный ViewModel - Repository вернет mock данные для Preview
    val viewModel: MenuViewModel = viewModel()
    MenuView(
        navController = rememberNavController(),
        viewModel = viewModel
    )
}