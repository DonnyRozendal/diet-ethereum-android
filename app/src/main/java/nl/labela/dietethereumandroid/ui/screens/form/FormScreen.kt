package nl.labela.dietethereumandroid.ui.screens.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nl.labela.dietethereumandroid.ui.Destinations.FORM
import nl.labela.dietethereumandroid.ui.Destinations.HOME
import nl.labela.dietethereumandroid.ui.screens.DeaButton
import nl.labela.dietethereumandroid.ui.screens.FormField
import nl.labela.dietethereumandroid.ui.screens.form.FormViewModel.Event.NavigateToHome
import nl.labela.dietethereumandroid.ui.theme.niceBlue
import nl.labela.dietethereumandroid.ui.utils.Observe

@Composable
fun FormScreen(viewModel: FormViewModel, navController: NavController) {
    viewModel.apply {
        Observe(
            navController = navController,
            eventFlow = eventFlow,
            handleEvent = ::handleFormEvents
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Please fill in your account information",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 32.dp)
            )
            Form(viewModel = viewModel)
        }
        val isLoading by viewModel.isLoading.observeAsState(initial = false)
        if (isLoading) {
            CircularProgressIndicator(
                color = niceBlue,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Form(viewModel: FormViewModel) {
    val email by viewModel.email.observeAsState(initial = "")
    val firstName by viewModel.firstName.observeAsState(initial = "")
    val lastName by viewModel.lastName.observeAsState(initial = "")
    val startWeight by viewModel.startWeight.observeAsState(initial = "")
    val goalWeight by viewModel.goalWeight.observeAsState(initial = "")

    val (emailFocus, firstNameFocus, lastNameFocus, startWeightFocus, goalWeightFocus) = FocusRequester.createRefs()

    Column(modifier = Modifier.padding(start = 32.dp)) {
        FormField(
            labelText = "Email",
            value = email,
            onValueChange = viewModel::onEmailChanged,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.focusOrder(emailFocus) { firstNameFocus.requestFocus() })
        FormField(
            labelText = "First name",
            value = firstName,
            onValueChange = viewModel::onFirstNameChanged,
            capitalization = KeyboardCapitalization.Words,
            modifier = Modifier.focusOrder(firstNameFocus) { lastNameFocus.requestFocus() })
        FormField(
            labelText = "Last name",
            value = lastName,
            onValueChange = viewModel::onLastNameChanged,
            capitalization = KeyboardCapitalization.Words,
            modifier = Modifier.focusOrder(lastNameFocus) { startWeightFocus.requestFocus() })
        FormField(
            labelText = "Start weight",
            value = startWeight,
            onValueChange = viewModel::onStartWeightChanged,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.focusOrder(startWeightFocus) { goalWeightFocus.requestFocus() })
        FormField(
            labelText = "Goal weight",
            value = goalWeight,
            onValueChange = viewModel::onGoalWeightChanged,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.focusOrder(goalWeightFocus)
        )
        DeaButton(
            text = "Next",
            onClick = viewModel::onDone,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

private fun handleFormEvents(event: FormViewModel.Event, navController: NavController) {
    when (event) {
        is NavigateToHome -> {
            navController.navigate(HOME) {
                popUpTo(FORM) { inclusive = true }
            }
        }
    }
}