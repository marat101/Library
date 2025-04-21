package ru.marat.feature_book.model

data class Rating(
    val star1: Int,
    val star2: Int,
    val star3: Int,
    val star4: Int,
    val star5: Int
) {
    val average: Float = calculateAverage()
    val common: Int = star1 + star2 + star3 + star4 + star5
    val max = arrayOf(star1, star2, star3, star4, star5).max()
    private fun calculateAverage(): Float {
        val totalVotes = star1 + star2 + star3 + star4 + star5
        if (totalVotes == 0) return 0f

        val weightedSum = star1 * 1 + star2 * 2 + star3 * 3 + star4 * 4 + star5 * 5
        return (weightedSum.toDouble() / totalVotes).toFloat()
    }
}