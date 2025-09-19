package com.sks.theyellowtable.screens.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.sks.theyellowtable.R
import com.sks.theyellowtable.database.model.MenuItemEntity
import com.sks.theyellowtable.network.APIClient
import com.sks.theyellowtable.network.APIClientImpl
import com.sks.theyellowtable.network.RemoteMenuRepository
import com.sks.theyellowtable.network.RemoteMenuRepositoryImpl
import com.sks.theyellowtable.repository.MenuRepository
import com.sks.theyellowtable.repository.MenuRepositoryImpl
import com.sks.theyellowtable.views.TopBar
import org.koin.androidx.compose.koinViewModel

// MARK: - View

@Composable
fun MenuView(
    navController: NavController,
    viewModel: MenuViewModel = koinViewModel()
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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "menu",
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchPhrase,
                    onValueChange = { searchPhrase = it },
                    label = { Text("Search") },
                    modifier = Modifier.weight(1f)
                )
                
                IconButton(
                    onClick = { orderMenuItems = !orderMenuItems }
                ) {
                    Icon(
                        imageVector = if (orderMenuItems)
                            Icons.Default.ArrowDownward
                        else
                            Icons.Default.SortByAlpha,
                        contentDescription = "Sort alphabetically",
                        tint = if (orderMenuItems) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            MenuItemsList(items = filteredMenuItems)
        }
    }
}

@Composable
private fun MenuItemsList(items: List<MenuItemEntity>) {
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

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MenuPreview() {
    val apiClient = APIClientImpl()
    val remoteMenuRepository = RemoteMenuRepositoryImpl(apiClient = apiClient)
    val localMenuRepository = MenuRepositoryImpl()
    val mockViewModel = MenuViewModel(
        remoteMenuRepository = remoteMenuRepository,
        localMenuRepository = localMenuRepository
    )
    MenuView(
        navController = rememberNavController(),
        viewModel = mockViewModel
    )
}