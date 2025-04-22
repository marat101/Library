package ru.marat.core_data.utils

fun calculateAverage(vararg values: Int): Float {
    val totalVotes = values.sum()
    if (totalVotes == 0) return 0f

    val weightedSum = values.mapIndexed { index, value ->
        value * (index + 1)
    } .sum()
    return (weightedSum.toDouble() / totalVotes).toFloat()
}