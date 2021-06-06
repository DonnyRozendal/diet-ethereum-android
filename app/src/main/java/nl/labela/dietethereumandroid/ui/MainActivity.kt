package nl.labela.dietethereumandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.labela.dietethereumandroid.ui.Destinations.HOME
import nl.labela.dietethereumandroid.ui.screens.HomeScreen
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeNavigation() }
    }

    @Composable
    fun ComposeNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = HOME) {
            composable(route = HOME) {
                HomeScreen(getViewModel())
            }
        }
    }

}