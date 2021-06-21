package nl.labela.dietethereumandroid.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import nl.labela.dietethereumandroid.ui.screens.BaseViewModel
import nl.labela.dietethereumandroid.ui.screens.CustomException
import timber.log.Timber

@Composable
fun <T> BaseViewModel.Observe(
    navController: NavController,
    eventFlow: Flow<T>,
    handleEvent: suspend (T, NavController) -> Unit,
    handleError: suspend (Exception, Context) -> Unit = { exception, context ->
        handleDefaultError(
            exception,
            context
        )
    }
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(true) {
        eventFlow.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
            handleEvent(it, navController)
        }
    }

    LaunchedEffect(true) {
        errorFlow.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
            handleError(it, context)
        }
    }
}

fun handleDefaultError(exception: Exception, context: Context) = when (exception) {
    is CustomException.NoInternetConnection -> {
        Toast.makeText(context, "No internet available", Toast.LENGTH_SHORT).show()
    }
    else -> Timber.i("Unhandled error: $exception")
}

@Composable
fun <T> Flow<T>.lifecycleAware(): Flow<T> {
    val lifecycleOwner = LocalLifecycleOwner.current

    return remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
}