package nl.labela.dietethereumandroid.ui.screens.home

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.labela.dietethereumandroid.data.EthereumRepository
import nl.labela.dietethereumandroid.models.WeightEntry
import nl.labela.dietethereumandroid.ui.screens.BaseViewModel
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : BaseViewModel() {

    val balance = MutableLiveData<Float>()
    val startWeight = MutableLiveData<Int>()
    val goalWeight = MutableLiveData<Int>()
    val weightEntries = MutableLiveData<List<WeightEntry>>()

    val newWeight = MutableLiveData<String>()

    init {
        runAsync {
            balance.postValue(ethereumRepository.getWalletBalance())
            startWeight.postValue(ethereumRepository.getUserStartWeight())
            goalWeight.postValue(ethereumRepository.getUserGoalWeight())
            refreshWeightEntries()

            ethereumRepository.getUserEmail()
        }
    }

    fun addWeightEntry() = runAsync {
        val newWeight = newWeight.value

        if (!newWeight.isNullOrEmpty()) {
            ethereumRepository.addWeightEntry(
                WeightEntry(
                    (newWeight.toDouble() * 1000).toInt(),
                    Instant.now().toEpochMilli()
                )
            )
            refreshWalletBalance()
            refreshWeightEntries()
        }
    }

    private suspend fun refreshWeightEntries() {
        weightEntries.postValue(ethereumRepository.getWeightEntries())
    }

    private suspend fun refreshWalletBalance() {
        balance.postValue(ethereumRepository.getWalletBalance())
    }

    fun receiveReward() = runAsync {
        ethereumRepository.receiveReward()
        refreshWalletBalance()
    }

    // 16 till 20 june
    fun addTestData() = runAsync {
        listOf(
            WeightEntry(79000, 1623848400000),
            WeightEntry(78000, 1623934800000),
            WeightEntry(77000, 1624021200000),
            WeightEntry(76000, 1624107600000),
            WeightEntry(75000, 1624194000000)
        ).forEach {
            ethereumRepository.addWeightEntry(it)
        }
        refreshWalletBalance()
        refreshWeightEntries()
    }

}