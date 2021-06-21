package nl.labela.dietethereumandroid.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.labela.dietethereumandroid.ui.Destinations.FORM
import nl.labela.dietethereumandroid.ui.Destinations.HOME
import nl.labela.dietethereumandroid.ui.Destinations.SIGNUP
import nl.labela.dietethereumandroid.ui.Destinations.SPLASH
import nl.labela.dietethereumandroid.ui.screens.splash.SplashViewModel.Event.*
import nl.labela.dietethereumandroid.ui.theme.niceBlue
import nl.labela.dietethereumandroid.ui.utils.Observe

@Composable
fun SplashScreen(viewModel: SplashViewModel, navController: NavController) {
    viewModel.apply {
        Observe(
            navController = navController,
            eventFlow = eventFlow,
            handleEvent = ::handleSplashEvents
        )
    }

    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = niceBlue,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

private fun handleSplashEvents(event: SplashViewModel.Event, navController: NavController) {
    when (event) {
        is NavigateToSignup -> navController.navigateFromSplash(SIGNUP)
        is NavigateToForm -> navController.navigateFromSplash(FORM)
        is NavigateToHome -> navController.navigateFromSplash(HOME)
    }
}

private fun NavController.navigateFromSplash(route: String) {
    navigate(route) { popUpTo(SPLASH) { inclusive = true } }
}