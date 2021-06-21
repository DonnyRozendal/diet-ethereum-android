package nl.labela.dietethereumandroid.models

import nl.labela.dietethereumandroid.solidity.Diet
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class WeightEntry(
    val grams: Int,
    val timestamp: Long
) {

    fun getDayOfYear(): Int {
        val instant = Instant.ofEpochMilli(timestamp)
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).dayOfYear
    }

}

fun List<Diet.WeightEntry>.toKotlinWeightEntries(): List<WeightEntry> {
    return map {
        WeightEntry(it.grams.toInt(), it.timestamp.toLong())
    }
}