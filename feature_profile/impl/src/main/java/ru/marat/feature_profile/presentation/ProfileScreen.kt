package ru.marat.feature_profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ru.marat.core_ui.theme.AppTheme
import ru.marat.feature_profile.api.R
import ru.marat.feature_profile.presentation.components.DefaultAvatar
import ru.marat.core_ui.components.app.profile_buttons.ProfileButton
import ru.marat.core_ui.components.app.profile_buttons.ProfileButtonData
import ru.marat.core_ui.components.common.calculateHorizontalPadding
import ru.marat.feature_profile.di.profileViewModel
import ru.marat.feature_settings.SettingsScreens
import ru.marat.library.ui.theme.LibraryTheme
import ru.marat.core_ui.R as CoreRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = profileViewModel()
) {
    val profileButtons = listOf(
        ProfileButtonData(
            text = stringResource(R.string.profile_action_history),
            icon = CoreRes.drawable.ic_history,
            action = { /*TODO navigation to history*/ }
        ),
        ProfileButtonData(
            text = stringResource(R.string.profile_action_my_library),
            icon = CoreRes.drawable.ic_book,
            action = { /*TODO navigation to my library*/ }
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.top_app_bar_screen_title),
                    fontWeight = FontWeight.SemiBold
                )
            },
            actions = {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                        .clickable(
                            indication = ripple(bounded = false, radius = 32.dp),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { viewModel.onSettingsClick() }
                        ),
                    painter = painterResource(CoreRes.drawable.ic_settings_outlined),
                    contentDescription = null,
                    tint = AppTheme.colorScheme.topAppBarActions
                )
            }
        )

        ProfileLayout(
            modifier = Modifier.fillMaxSize(),
            imageUrl = null,
            actions = profileButtons
        )
    }
}

@Composable
fun ProfileLayout(
    modifier: Modifier = Modifier,
    username: String? = null,
    imageUrl: String? = null,
    actions: List<ProfileButtonData> = emptyList()
) {
    val context = LocalContext.current
    val colorScheme = AppTheme.colorScheme
    val defaultImage = remember(colorScheme) {
        DefaultAvatar(
            context,
            backgroundColor = colorScheme.avatarBackground,
            imageColor = colorScheme.avatarIcon
        )
    }
    val scrollState = rememberScrollState()
    val horizontalPadding = calculateHorizontalPadding()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .placeholder(defaultImage)
                .error(defaultImage)
                .build(),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            text = username ?: "Unnamed",
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = colorScheme.profileText
        )
        Spacer(Modifier.height(60.dp))
        val buttonsModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp,)
            .padding(horizontalPadding)
        actions.fastForEach {
            ProfileButton(
                modifier = buttonsModifier,
                data = it
            )
        }
    }
}

@Preview
@Composable
fun ProfileLayoutLightPreview() {
    LibraryTheme(false) {
        val profileButtons = remember {
            listOf(
                ProfileButtonData(
                    text = "История",
                    icon = CoreRes.drawable.ic_history,
                    action = {  }
                ),
            )
        }
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            ProfileLayout(
                modifier = Modifier.fillMaxSize(),
                actions = profileButtons
            )
        }
    }
}

@Preview
@Composable
fun ProfileLayoutDarkPreview() {
    LibraryTheme(true) {
        val profileButtons = remember {
            listOf(
                ProfileButtonData(
                    text = "История",
                    icon = CoreRes.drawable.ic_history,
                    action = {  }
                ),
            )
        }
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            ProfileLayout(
                modifier = Modifier.fillMaxSize(),
                actions = profileButtons
            )
        }
    }
}