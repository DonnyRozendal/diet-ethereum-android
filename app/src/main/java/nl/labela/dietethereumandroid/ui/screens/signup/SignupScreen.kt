package nl.labela.dietethereumandroid.ui.screens.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.labela.dietethereumandroid.ui.Destinations.SETUP
import nl.labela.dietethereumandroid.ui.screens.DeaButton

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen(rememberNavController())
}

@Composable
fun SignupScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 48.dp)
        ) {
            Text(
                text = "Welcome!",
                color = Color.Black,
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Use this app to keep track of your weight loss goals. If you manage to reach your goal, you will receive 0.1 ETH! Press this button to setup your wallet.",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, end = 64.dp)
            )
            DeaButton(
                text = "Initiate setup",
                onClick = { navController.navigate(SETUP) },
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}