package nl.labela.dietethereumandroid.ui.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    private val errorChannel = Channel<Exception>(Channel.BUFFERED)
    val errorFlow = errorChannel.receiveAsFlow()

    fun runAsync(tryFunction: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                tryFunction()
            } catch (exception: Exception) {
                Timber.e(exception)
                errorChannel.send(exception)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

}

sealed class CustomException : Exception() {
    object NoInternetConnection : CustomException()
    object NoUserFoundException : CustomException()
}