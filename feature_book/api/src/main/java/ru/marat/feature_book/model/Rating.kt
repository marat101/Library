package ru.marat.feature_book.model

import ru.marat.core_data.utils.calculateAverage

data class Rating(
    val star1: Int,
    val star2: Int,
    val star3: Int,
    val star4: Int,
    val star5: Int
) {
    val average: Float = calculateAverage(star1, star2, star3, star4, star5)
    val common: Int = star1 + star2 + star3 + star4 + star5
    val max = arrayOf(star1, star2, star3, star4, star5).max()
}