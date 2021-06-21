package nl.labela.dietethereumandroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import nl.labela.dietethereumandroid.ui.theme.niceBlue

@Composable
fun DeaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val alpha = if (enabled) 1.0F else 0.5F

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = niceBlue,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        modifier = modifier.alpha(alpha = alpha)
    ) {
        Text(text = text)
    }
}

@Composable
fun FormField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            autoCorrect = false,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = modifier.padding(top = 16.dp)
    )
}

var showDialog by mutableStateOf(false)
@Composable
fun RewardDialog(onClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = {
                showDialog = false
                onClick()
            }) {
                Text(
                    text = "OK",
                    color = Color.Black
                )
            }
        },
        title = { Text(text = "Congratulations!") },
        text = { Text(text = "You just achieved a great milestone! Click OK to receive 0.1 ETH!") })
}