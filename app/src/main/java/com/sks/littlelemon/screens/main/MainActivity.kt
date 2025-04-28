package com.sks.littlelemon.screens.main

import com.sks.littlelemon.screens.navigation.BottomBar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sks.littlelemon.destinations.DishDetails
import com.sks.littlelemon.destinations.Home
import com.sks.littlelemon.destinations.Profile
import com.sks.littlelemon.screens.dishDetails.DishDetailsScreen
import com.sks.littlelemon.screens.home.HomeScreen
import com.sks.littlelemon.screens.login.LoginScreen
import com.sks.littlelemon.screens.profile.ProfileScreen
import com.sks.littlelemon.ui.theme.LittleLemonTheme

// MARK: - Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LittleLemonTheme {
                MainView()
            }
        }
    }
}

// MARK: - Private Functions

@Composable
private fun shouldShowBottomBar(
    isUserSignedIn: Boolean,
    currentRoute: String?
): Boolean {
    val shouldShow = isUserSignedIn && currentRoute?.startsWith(DishDetails.route) != true
    Log.d("MainActivity", "Current route: $currentRoute, Should show bottom bar: $shouldShow")
    return shouldShow
}

// MARK: - View

@Composable
fun MainView(isUserSignedIn: Boolean = false) {
    var isUserSignedInState by rememberSaveable { mutableStateOf(isUserSignedIn) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if (shouldShowBottomBar(isUserSignedInState, currentRoute)) {
                BottomBar(navController = navController)
            }
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (isUserSignedInState) {
                    NavHost(navController = navController, startDestination = Home.route) {
                        composable(Home.route) {
                            HomeScreen(navController)
                        }
                        composable(Profile.route) {
                            ProfileScreen(onUserSignedOut = {
                                isUserSignedInState = false
                                navController.navigate(Home.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            })
                        }
                        composable(
                            route = DishDetails.route + "/{${DishDetails.dishId}}",
                            arguments = listOf(navArgument(DishDetails.dishId) { type = NavType.IntType })
                        ) {
                            val id = requireNotNull(it.arguments?.getInt(DishDetails.dishId)) { "Dish id is null" }
                            DishDetailsScreen(id = id, navController = navController)
                        }
                    }
                } else {
                    LoginScreen(onUserSignedIn = {
                        isUserSignedInState = true
                    })
                }
            }
        }
    )
}

// MARK: - Preview

@Preview(name = "MainView - User Not Signed In", showBackground = true, showSystemUi = true)
@Composable
private fun MainPreviewUserNotSignedIn() {
    MainView(isUserSignedIn = false)
}

@Preview(name = "MainView - User Signed In", showBackground = true, showSystemUi = true)
@Composable
private fun MainPreviewUserSignedIn() {
    MainView(isUserSignedIn = true)
}
