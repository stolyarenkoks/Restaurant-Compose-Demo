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
import com.sks.littlelemon.destinations.Destinations
import com.sks.littlelemon.destinations.Home
import com.sks.littlelemon.destinations.Profile

@Composable
fun BottomBar(navController: NavController) {
    val destinations = listOf<Destinations>(Home, Profile)
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
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
                selected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
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