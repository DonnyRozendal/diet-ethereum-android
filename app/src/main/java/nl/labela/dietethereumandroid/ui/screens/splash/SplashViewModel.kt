package nl.labela.dietethereumandroid.ui.screens.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import nl.labela.dietethereumandroid.data.EthereumRepository
import nl.labela.dietethereumandroid.ui.screens.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : BaseViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        runAsync {
            when {
                ethereumRepository.getWallet().isNullOrEmpty() -> {
                    eventChannel.send(Event.NavigateToSignup)
                }
                ethereumRepository.getUserEmail().isEmpty() -> {
                    eventChannel.send(Event.NavigateToForm)
                }
                else -> {
                    eventChannel.send(Event.NavigateToHome)
                }
            }
        }
    }

    sealed class Event {
        object NavigateToSignup : Event()
        object NavigateToForm : Event()
        object NavigateToHome : Event()
    }

}