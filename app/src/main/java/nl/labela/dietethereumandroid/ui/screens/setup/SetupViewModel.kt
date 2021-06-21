package nl.labela.dietethereumandroid.ui.screens.setup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import nl.labela.dietethereumandroid.data.EthereumRepository
import nl.labela.dietethereumandroid.ui.screens.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : BaseViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        runAsync {
            ethereumRepository.createWallet()
            val user = ethereumRepository.getUserEmail()

            if (user.isEmpty()) {
                eventChannel.send(Event.NavigateToForm)
            } else {
                eventChannel.send(Event.NavigateToHome)
            }
        }
    }

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToForm : Event()
    }

}