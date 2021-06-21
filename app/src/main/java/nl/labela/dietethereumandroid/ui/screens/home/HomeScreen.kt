package nl.labela.dietethereumandroid.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import nl.labela.dietethereumandroid.models.WeightEntry
import nl.labela.dietethereumandroid.ui.screens.DeaButton
import nl.labela.dietethereumandroid.ui.screens.FormField
import nl.labela.dietethereumandroid.ui.screens.RewardDialog
import nl.labela.dietethereumandroid.ui.screens.showDialog
import nl.labela.dietethereumandroid.ui.theme.niceBlue
import java.time.Year
import kotlin.math.roundToInt

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val balance by viewModel.balance.observeAsState(initial = 0F)
    val balanceText = if (balance > 0F) "%.4f ETH".format(balance) else "Loading..."

    val startWeight by viewModel.startWeight.observeAsState()
    val goalWeight by viewModel.goalWeight.observeAsState()

    val weightEntries by viewModel.weightEntries.observeAsState(initial = emptyList())

    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = niceBlue,
                modifier = Modifier.size(80.dp)
            )
        }
    }

    if (showDialog) RewardDialog { viewModel.receiveReward() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        WalletBalance(viewModel = viewModel, balanceText = balanceText)
        WeightProgress(
            startWeight = startWeight,
            goalWeight = goalWeight,
            weightEntries = weightEntries
        )
        Form(viewModel = viewModel)
        WeightChart(weightEntries = weightEntries)
    }
}

@Composable
private fun WalletBalance(viewModel: HomeViewModel, balanceText: String) {
    Text(
        text = "Wallet balance",
        color = Color.Black,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable { viewModel.addTestData() }
    )
    Text(
        text = balanceText,
        color = Color.Black,
        fontSize = 18.sp,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun WeightProgress(startWeight: Int?, goalWeight: Int?, weightEntries: List<WeightEntry>) {
    val currentWeight = weightEntries.maxByOrNull {
        it.timestamp
    }?.grams
    val currentWeightText = currentWeight
        ?.toDouble()
        ?.div(1000)
        ?.toString()
        ?.plus(" kg") ?: ""

    val progress = if (startWeight != null && goalWeight != null && currentWeight != null) {
        val numerator = currentWeight.minus(startWeight).toDouble()
        val denominator = goalWeight.minus(startWeight).toDouble()
        val progress = numerator.div(denominator).toFloat()
        when {
            progress > 1.0F -> 1.0F
            progress < 0F -> 0F
            else -> progress
        }
    } else {
        0F
    }

    val percentageText = if (progress > 0F) {
        val percentage = progress.toDouble().times(100).roundToInt()
        if (percentage > 100) {
            "100%"
        } else {
            "$percentage%"
        }
    } else {
        "0%"
    }

    LaunchedEffect(percentageText) {
        if (percentageText == "100%") {
            showDialog = true
        }
    }

    Text(
        text = "Current weight: $currentWeightText",
        color = Color.Black,
        fontSize = 18.sp,
        modifier = Modifier.padding(top = 32.dp)
    )
    Text(
        text = "Weight progress:",
        color = Color.Black,
        fontSize = 18.sp,
        modifier = Modifier.padding(top = 32.dp)
    )
    LinearProgressIndicator(
        progress = progress,
        color = niceBlue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = startWeight?.toDouble()?.div(1000)?.toString() ?: "",
            color = Color.Black,
            fontSize = 14.sp
        )
        Text(
            text = percentageText,
            color = Color.Black,
            fontSize = 16.sp
        )
        Text(
            text = goalWeight?.toDouble()?.div(1000)?.toString() ?: "",
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun Form(viewModel: HomeViewModel) {
    val newWeight by viewModel.newWeight.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    Column {
        Text(
            text = "Add new weight",
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
        FormField(
            labelText = "Weight",
            value = newWeight,
            onValueChange = { viewModel.newWeight.value = it },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )
        DeaButton(
            text = "Send",
            onClick = viewModel::addWeightEntry,
            enabled = !isLoading && newWeight.isNotEmpty(),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun WeightChart(weightEntries: List<WeightEntry>) {
    if (weightEntries.isNotEmpty()) {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    xAxis.granularity = 1F
                    xAxis.labelCount = 1
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.valueFormatter = object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            return "June"
                        }
                    }
                }
            },
            update = { view ->
                view.invalidate()
                val newestUniqueDates = weightEntries.groupBy {
                    it.getDayOfYear()
                }.values.map { list ->
                    list.maxByOrNull {
                        it.timestamp
                    }!!
                }
                val entries = newestUniqueDates.map {
                    BarEntry(it.getDayOfYear().toFloat(), it.grams.div(1000).toFloat())
                }
                val dataSet = BarDataSet(entries, "Weight").apply {
                    valueFormatter = object : ValueFormatter() {
                        override fun getBarLabel(barEntry: BarEntry): String {
                            val date = Year.of(2021).atDay(barEntry.x.toInt())
                            val day = date.dayOfMonth
                            val month = date.monthValue
                            return "$day/$month"
                        }
                    }
                    valueTextSize = 12.0F
                }
                val data = BarData(dataSet)
                view.data = data
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        )
    }
}