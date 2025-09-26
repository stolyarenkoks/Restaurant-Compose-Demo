package com.sks.theyellowtable.views

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sks.theyellowtable.destinations.Home
import com.sks.theyellowtable.destinations.Profile
import com.sks.theyellowtable.ui.theme.TheYellowTableColor
import com.sks.theyellowtable.ui.theme.TheYellowTableTheme

// MARK: - View

@Composable
fun BottomBar(navController: NavController) {
    val destinations = listOf(Home, Profile)
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        tonalElevation = 0.dp
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

// MARK: - Preview

@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}