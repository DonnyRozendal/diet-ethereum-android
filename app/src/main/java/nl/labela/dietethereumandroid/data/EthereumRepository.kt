package nl.labela.dietethereumandroid.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.runBlocking
import nl.labela.dietethereumandroid.models.WeightEntry
import nl.labela.dietethereumandroid.models.toKotlinWeightEntries
import nl.labela.dietethereumandroid.solidity.Diet
import nl.labela.dietethereumandroid.ui.screens.CustomException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class EthereumRepository @Inject constructor(
    private val applicationContext: Context,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        const val WALLET_FOLDER_PATH = "/ethereum"
        const val INFURA_PROJECT_URL = "https://rinkeby.infura.io/v3/b7912c17665442f7931b6dbf14037023"
        const val WALLET_PASSWORD = "wallet_password"
        const val CONTRACT_ADDRESS = "0x125C87bA93f06ecCaadFDF73Ee89c425d2D76306"
        const val REWARD_WALLET_PRIVATE_KEY = "ece47afee2067b7114f1476a9da3cb693b0d51f3ac55e792ba480cfd82ae7535"
        const val REWARD = "0.1"
    }

    private val walletFolder = File(applicationContext.filesDir, WALLET_FOLDER_PATH)

    private val contract by lazy {
        runBlocking {
            Diet.load(
                CONTRACT_ADDRESS,
                createWeb3j(),
                loadCredentials(),
                DefaultGasProvider()
            )
        }
    }

    private suspend fun getWalletFile(): File {
        val walletFileName = dataStore.get(WALLET_FILE_NAME)
        return File(applicationContext.filesDir, "/ethereum/$walletFileName")
    }

    private fun createWeb3j(): Web3j {
        return Web3j.build(HttpService(INFURA_PROJECT_URL))
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun loadCredentials(): Credentials {
        return WalletUtils.loadCredentials(WALLET_PASSWORD, getWalletFile()).also {
            println("Debug: wallet address is ${it.address}")
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun createWallet(): String {
        val walletFileName = WalletUtils.generateNewWalletFile(WALLET_PASSWORD, walletFolder, false)
        return dataStore.store(WALLET_FILE_NAME, walletFileName)!!.also {
            println("Debug: wallet created $it")
        }
    }

    suspend fun getWallet(): String? {
        return dataStore.get(WALLET_FILE_NAME)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getWalletBalance(): Float {
        val ethereumDivideFactor = 1000000000000000000F

        return createWeb3j().ethGetBalance(
            loadCredentials().address,
            DefaultBlockParameterName.LATEST
        ).send().balance.toFloat().div(ethereumDivideFactor).also {
            println("Debug: wallet balance $it")
        }
    }

    // Contract functions

    suspend fun createUser(
        email: String,
        firstName: String,
        lastName: String,
        startWeight: String,
        goalWeight: String
    ) {
        contract.createUser(
            email,
            firstName,
            lastName,
            (startWeight.toDouble() * 1000).toInt().toString(),
            (goalWeight.toDouble() * 1000).toInt().toString()
        )?.send().also { println("Debug: receipt $it") }
    }

    suspend fun getUserEmail(): String {
        return contract.userEmail.send().also { println("Debug: user $it") }
            ?: throw CustomException.NoUserFoundException
    }

    suspend fun getUserStartWeight(): Int {
        return contract.userStartWeight.send().toInt().also { println("Debug: start weight $it") }
    }

    suspend fun getUserGoalWeight(): Int {
        return contract.userGoalWeight.send().toInt().also { println("Debug: goal weight $it") }
    }

    suspend fun addWeightEntry(weightEntry: WeightEntry) {
        val grams = BigInteger(weightEntry.grams.toString())
        val timestamp = BigInteger(weightEntry.timestamp.toString())

        contract.addWeightEntry(grams, timestamp).send()
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getWeightEntries(): List<WeightEntry> {
        return (contract.weightEntries.send() as List<Diet.WeightEntry>)
            .toKotlinWeightEntries()
            .onEach { entry ->
                println("Debug: entry ${entry.grams}, ${entry.timestamp}")
            }
    }

    suspend fun clearWeightEntries() {
        contract.clearWeightEntries().send().also { println("Debug: receipt $it") }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun receiveReward() {
        Transfer.sendFunds(
            createWeb3j(),
            Credentials.create(REWARD_WALLET_PRIVATE_KEY),
            loadCredentials().address,
            BigDecimal(REWARD),
            Convert.Unit.ETHER
        ).send()
    }

}