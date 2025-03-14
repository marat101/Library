package ru.marat.feature_root.ui.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlin.enums.EnumEntries

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedButton: NavigationButton? = null,
    buttons: EnumEntries<NavigationButton>,
    onClick: (NavigationButton) -> Unit,
) {
    NavigationBar(
        modifier = modifier
    ) {
        buttons.forEach {
            val isSelected = selectedButton == it
            NavigationBarItem(
                selected = isSelected,
                icon = {
                    Icon(
                        painter = if (isSelected) painterResource(it.selectedIcon)
                        else painterResource(it.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(it.title))
                },
                onClick = {
                    onClick(it)
                }
            )
        }
    }
}
