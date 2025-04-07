package ru.marat.feature_root.ui

import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.library.ui.theme.Theme

data class RootState(
    val theme: Theme? = null,
    val activeButton: NavigationButton = NavigationButton.MAIN
)