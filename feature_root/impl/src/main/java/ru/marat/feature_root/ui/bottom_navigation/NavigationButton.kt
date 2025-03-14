package ru.marat.feature_root.ui.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_profile.ProfileScreen
import ru.marat.feature_root.api.R
import ru.marat.navigation_api.Screen
import ru.marat.core_ui.R as CoreRes

enum class NavigationButton(
    val route: Screen,
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    @DrawableRes
    val selectedIcon: Int = icon,
) {
    MAIN(
        route = HomeScreen,
        title = R.string.navigation_button_home,
        icon = ru.marat.core_ui.R.drawable.ic_book
    ),
    PROFILE(
        route = ProfileScreen,
        title = R.string.navigation_button_profile,
        icon = CoreRes.drawable.ic_person_outlined,
        selectedIcon = CoreRes.drawable.ic_person
    )
}