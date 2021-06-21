package nl.labela.dietethereumandroid.ui.screens.form

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import nl.labela.dietethereumandroid.data.EthereumRepository
import nl.labela.dietethereumandroid.ui.screens.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : BaseViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    val email = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val startWeight = MutableLiveData<String>()
    val goalWeight = MutableLiveData<String>()

    fun onEmailChanged(email: String) { this.email.value = email }
    fun onFirstNameChanged(firstName: String) { this.firstName.value = firstName }
    fun onLastNameChanged(lastName: String) { this.lastName.value = lastName }
    fun onStartWeightChanged(startWeight: String) { this.startWeight.value = startWeight }
    fun onGoalWeightChanged(goalWeight: String) { this.goalWeight.value = goalWeight }

    init {
        email.value = "donny.rozendal@hva.nl"
        firstName.value = "Donny"
        lastName.value = "Rozendal"
        startWeight.value = "81.5"
        goalWeight.value = "70.0"
    }

    fun onDone() = runAsync {
        val email = email.value
        val firstName = firstName.value
        val lastName = lastName.value
        val startWeight = startWeight.value
        val goalWeight = goalWeight.value

        if (
            !email.isNullOrEmpty() &&
            !firstName.isNullOrEmpty() &&
            !lastName.isNullOrEmpty() &&
            !startWeight.isNullOrEmpty() &&
            !goalWeight.isNullOrEmpty()
        ) {
            ethereumRepository.createUser(
                email,
                firstName,
                lastName,
                startWeight,
                goalWeight
            )
            eventChannel.send(Event.NavigateToHome)
        }
    }

    sealed class Event {
        object NavigateToHome : Event()
    }

}