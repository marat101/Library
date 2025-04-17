package ru.marat.feature_settings.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import ru.marat.core_ui.components.app.profile_buttons.ProfileButton
import ru.marat.core_ui.components.app.profile_buttons.ProfileButtonData
import ru.marat.core_ui.components.common.calculateHorizontalPadding
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_settings.api.R
import ru.marat.feature_settings.di.contatiners.settingsViewModel
import ru.marat.feature_settings.presentation.common.ConfirmDialog
import ru.marat.feature_settings.presentation.components.ThemeSelector
import ru.marat.core_ui.R as CoreRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = settingsViewModel(),
) {
    val state = viewModel.state.collectAsState()

    val buttons = listOf<ProfileButtonData>( //todo res
        ProfileButtonData(
            text = "Обратная связь",
            icon = CoreRes.drawable.ic_feedback_outlined,
            textColor = AppTheme.colorScheme.profileText,
            arrow = true,
            action = {

            }
        ),
        ProfileButtonData(
            text = "О приложении",
            textColor = AppTheme.colorScheme.profileText,
            arrow = true,
            action = {
                // todo logout dialog
            }
        ),
        ProfileButtonData(
            text = "Выйти из аккаунта",
            icon = CoreRes.drawable.ic_exit,
            textColor = MaterialTheme.colorScheme.error,
            action = {
                viewModel.onLogoutClick()
            }
        )
    )
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = stringResource(R.string.top_app_bar_title))
            },
            navigationIcon = {
                IconButton(
                    onClick = { viewModel.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        )
        val horizontalPadding = calculateHorizontalPadding()
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(top = 16.dp),
                text = stringResource(id = R.string.settings_theme),
                color = AppTheme.colorScheme.settingsCategory,
                fontSize = 15.sp,
                fontWeight = W500,
            )
            ThemeSelector(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .padding(horizontalPadding)
                    .padding(top = 10.dp),
                viewModel = viewModel
            )
            Spacer(Modifier.size(30.dp))
            Text(
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(top = 16.dp),
                text = "Поддержка",
                color = AppTheme.colorScheme.settingsCategory,
                fontSize = 15.sp,
                fontWeight = W500,
            )
            buttons.fastForEachIndexed { index, it ->
                if (index == buttons.lastIndex) {
                    Spacer(Modifier.size(30.dp))
                    Text(
                        modifier = Modifier
                            .padding(horizontalPadding)
                            .padding(top = 16.dp),
                        text = "Аккаунт",
                        color = AppTheme.colorScheme.settingsCategory,
                        fontSize = 15.sp,
                        fontWeight = W500,
                    )
                }

                ProfileButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontalPadding)
                        .padding(vertical = 10.dp),
                    data = it
                )
            }
        }
    }
    ConfirmDialog(
        visible = state.value.confirmExitDialog,
        onConfirm = { viewModel.onLogoutConfirmClick() }
    ) {
        viewModel.onCloseConfirmDialog()
    }
}