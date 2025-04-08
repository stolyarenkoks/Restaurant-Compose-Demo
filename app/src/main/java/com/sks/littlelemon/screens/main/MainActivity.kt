package com.sks.littlelemon.screens.main

import HomeScreen
import LoginScreen
import android.os.Bundle
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sks.littlelemon.ui.theme.LittleLemonTheme

// MARK: - Activity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LittleLemonTheme {
                MainView(isUserSignedIn = false)
            }
        }
    }
}

// MARK: - View

@Composable
fun MainView(isUserSignedIn: Boolean) {
    var isUserSignedIn by remember { mutableStateOf(isUserSignedIn) }

    Scaffold(
        modifier = Modifier,
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (isUserSignedIn) {
                    HomeScreen(onUserSignedOut = {
                        isUserSignedIn = false
                    })
                } else {
                    LoginScreen(onUserSignedIn = {
                        isUserSignedIn = true
                    })
                }

            }
        }
    )
}

// MARK: - Preview

@Preview(name = "MainView - User Not Signed In", showBackground = true, showSystemUi = true)
@Composable
fun MainPreviewUserNotSignedIn() {
    MainView(isUserSignedIn = false)
}

@Preview(name = "MainView - User Signed In", showBackground = true, showSystemUi = true)
@Composable
fun MainPreviewUserSignedIn() {
    MainView(isUserSignedIn = true)
}