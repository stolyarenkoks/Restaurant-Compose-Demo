package com.sks.littlelemon.screens.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.destinations.Home
import com.sks.littlelemon.destinations.Profile
import com.sks.littlelemon.ui.theme.LittleLemonColor

@Composable
fun BottomBar(navController: NavController) {
    val destinations = listOf(Home, Profile)
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = LittleLemonColor.lightGray
    ) {
        destinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                label = {
                    destination.title?.let {
                        Text(it)
                    }
                },
                icon = {
                    destination.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = destination.title
                        )
                    }
                },
                selected = selectedIndex.intValue == index,
                onClick = {
                    selectedIndex.intValue = index
                    navController.navigate(destination.route) {
                        popUpTo("home")
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}