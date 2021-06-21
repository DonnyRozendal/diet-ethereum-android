package nl.labela.dietethereumandroid.ui.screens.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nl.labela.dietethereumandroid.ui.Destinations.FORM
import nl.labela.dietethereumandroid.ui.Destinations.HOME
import nl.labela.dietethereumandroid.ui.Destinations.SIGNUP
import nl.labela.dietethereumandroid.ui.screens.setup.SetupViewModel.Event.NavigateToForm
import nl.labela.dietethereumandroid.ui.screens.setup.SetupViewModel.Event.NavigateToHome
import nl.labela.dietethereumandroid.ui.theme.niceBlue
import nl.labela.dietethereumandroid.ui.utils.Observe

@Composable
fun SetupScreen(viewModel: SetupViewModel, navController: NavController) {
    viewModel.apply {
        Observe(
            navController = navController,
            eventFlow = eventFlow,
            handleEvent = ::handleSetupEvents
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
                Text(
                    text = "Creating wallet...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
    }
}

private fun handleSetupEvents(event: SetupViewModel.Event, navController: NavController) {
    when (event) {
        is NavigateToHome -> {
            navController.navigate(HOME) {
                popUpTo(SIGNUP) { inclusive = true }
            }
        }
        is NavigateToForm -> {
            navController.navigate(FORM) {
                popUpTo(SIGNUP) { inclusive = true }
            }
        }
    }
}