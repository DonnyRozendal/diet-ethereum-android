package nl.labela.dietethereumandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.labela.dietethereumandroid.ui.Destinations.FORM
import nl.labela.dietethereumandroid.ui.Destinations.HOME
import nl.labela.dietethereumandroid.ui.Destinations.SETUP
import nl.labela.dietethereumandroid.ui.Destinations.SIGNUP
import nl.labela.dietethereumandroid.ui.Destinations.SPLASH
import nl.labela.dietethereumandroid.ui.screens.form.FormScreen
import nl.labela.dietethereumandroid.ui.screens.home.HomeScreen
import nl.labela.dietethereumandroid.ui.screens.setup.SetupScreen
import nl.labela.dietethereumandroid.ui.screens.signup.SignupScreen
import nl.labela.dietethereumandroid.ui.screens.splash.SplashScreen
import nl.labela.dietethereumandroid.ui.theme.niceBlue
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        File(filesDir, "/ethereum").mkdirs()
        setContent {
            MaterialTheme(colors = lightColors(primary = niceBlue)) {
                ComposeNavigation()
            }
        }
    }

    @Composable
    fun ComposeNavigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = SPLASH) {
            composable(route = SPLASH) {
                SplashScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable(route = SIGNUP) {
                SignupScreen(navController = navController)
            }
            composable(route = SETUP) {
                SetupScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable(route = FORM) {
                FormScreen(viewModel = hiltViewModel(), navController = navController)
            }
            composable(route = HOME) {
                HomeScreen(viewModel = hiltViewModel())
            }
        }
    }

}