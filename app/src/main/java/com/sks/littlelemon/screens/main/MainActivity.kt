package com.sks.littlelemon.screens.main

import android.os.Build
import com.sks.littlelemon.views.BottomBar
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
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
import com.sks.littlelemon.screens.cart.CartView
import com.sks.littlelemon.screens.dishDetails.DishDetailsView
import com.sks.littlelemon.screens.home.HomeView
import com.sks.littlelemon.screens.login.LoginView
import com.sks.littlelemon.screens.profile.ProfileView
import com.sks.littlelemon.ui.theme.LittleLemonTheme

// MARK: - Activity

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }

        setContent {
            LittleLemonTheme {
                MainView()
            }
        }
    }
}

// MARK: - Private View Components

@Composable
private fun MainView(isUserSignedIn: Boolean = false) {
    var isUserSignedInState by rememberSaveable { mutableStateOf(isUserSignedIn) }
    var username by rememberSaveable { mutableStateOf("") }
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
                            HomeView(navController)
                        }
                        composable(Profile.route) {
                            ProfileView(
                                username = username,
                                onUserSignedOut = {
                                    isUserSignedInState = false
                                    username = ""
                                    navController.navigate(Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(
                            route = DishDetails.route + "/{${DishDetails.DISH_ID}}",
                            arguments = listOf(navArgument(DishDetails.DISH_ID) { type = NavType.IntType })
                        ) {
                            val id = requireNotNull(it.arguments?.getInt(DishDetails.DISH_ID)) { "Dish id is null" }
                            DishDetailsView(id = id, navController = navController)
                        }
                        composable("cart") {
                            CartView(navController = navController)
                        }
                    }
                } else {
                    LoginView(
                        onUserSignedIn = { enteredUsername ->
                            username = enteredUsername
                            isUserSignedInState = true
                        }
                    )
                }
            }
        }
    )
}

// MARK: - Private Functions

@Composable
private fun shouldShowBottomBar(
    isUserSignedIn: Boolean,
    currentRoute: String?
): Boolean {
    val shouldShow = isUserSignedIn && 
        currentRoute?.startsWith(DishDetails.route) != true && 
        currentRoute != "cart"
    Log.d("MainActivity", "Current route: $currentRoute, Should show bottom bar: $shouldShow")
    return shouldShow
}

// MARK: - Preview

@Preview(name = "User Not Signed In", showBackground = true, showSystemUi = true)
@Composable
private fun MainPreviewUserNotSignedIn() {
    MainView(isUserSignedIn = false)
}

@Preview(name = "User Signed In", showBackground = true, showSystemUi = true)
@Composable
private fun MainPreviewUserSignedIn() {
    MainView(isUserSignedIn = true)
}
