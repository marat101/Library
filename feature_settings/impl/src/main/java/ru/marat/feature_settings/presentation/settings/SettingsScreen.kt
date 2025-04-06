package ru.marat.feature_settings.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_settings.presentation.components.ThemeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val insets = WindowInsets.statusBars
    Column(
        modifier = modifier
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = "Настройки") // todo res
            },
            navigationIcon = {
                IconButton(
                    onClick = {navController.navigateUp()}
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
//                        tint = AppTheme.colorScheme.topAppBarActions
                    )
                }
            }
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
            text = "Тема оформления", //todo res
            color = AppTheme.colorScheme.settingsCategory,
            fontSize = 15.sp,
            fontWeight = W500,
        )
        ThemeSelector(modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 10.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "SettingsScreen",
            color = AppTheme.colorScheme.profileText
        )
    }
}