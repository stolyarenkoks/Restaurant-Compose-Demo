package com.sks.littlelemon.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.screens.home.LowerPanel
import com.sks.littlelemon.screens.topBar.TopAppBar

private const val TAG = "LITTLE_LEMON_HOME"

@Composable
fun HomeScreen(navController: NavHostController, onUserSignedOut: () -> Unit) {
    Log.d(TAG, "=== HOME SCREEN RENDERED ===")
    HomeView(navController = navController, onUserSignedOut = onUserSignedOut)
}

@Composable
private fun HomeView(navController: NavHostController, onUserSignedOut: () -> Unit = {}) {
    Log.d(TAG, "Rendering basic HomeView")
    val dishes = DishRepository.dishes
    Log.d(TAG, "Got ${dishes.size} dishes from repository")
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar()
        UpperPanel()
        LowerPanel(navController = navController, dishes = dishes)
    }
}