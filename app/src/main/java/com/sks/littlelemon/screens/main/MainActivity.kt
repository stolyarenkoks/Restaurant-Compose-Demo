package com.sks.littlelemon.screens.main

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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sks.littlelemon.router.Router
import com.sks.littlelemon.screens.home.HomeScreen
import com.sks.littlelemon.screens.login.LoginScreen
import com.sks.littlelemon.screens.dishDetails.DishDetailsScreen
import com.sks.littlelemon.ui.theme.LittleLemonTheme

private const val TAG = "LITTLE_LEMON_MAIN"

// MARK: - Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "=== MAIN ACTIVITY CREATED ===")

        setContent {
            LittleLemonTheme {
                MainView()
            }
        }
    }
}

// MARK: - View

@Composable
fun MainView(isUserSignedIn: Boolean = false) {
    var isUserSignedInState by rememberSaveable { mutableStateOf(isUserSignedIn) }
    Log.d(TAG, "=== MAIN VIEW RENDERED, LOGIN STATE: $isUserSignedInState ===")
    
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier,
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (isUserSignedInState) {
                    Log.d(TAG, "=== SHOWING HOME SCREEN ===")
                    NavHost(navController = navController, startDestination = Router.homeRoute) {
                        composable(Router.homeRoute) {
                            HomeScreen(navController, onUserSignedOut = {
                                Log.d(TAG, "=== USER SIGNED OUT ===")
                                isUserSignedInState = false
                            })
                        }
                        composable(
                            Router.dishDetailsRoute + "/{${Router.dishDetailsArgDishId}}",
                            arguments = listOf(navArgument(Router.dishDetailsArgDishId) { type = NavType.IntType })
                        ) {
                            val id = requireNotNull(it.arguments?.getInt(Router.dishDetailsArgDishId)) { "Dish id is null" }
                            DishDetailsScreen(id)
                        }
                    }
                } else {
                    Log.d(TAG, "=== SHOWING LOGIN SCREEN ===")
                    LoginScreen(onUserSignedIn = {
                        Log.d(TAG, "=== USER SIGNED IN CALLBACK RECEIVED ===")
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
