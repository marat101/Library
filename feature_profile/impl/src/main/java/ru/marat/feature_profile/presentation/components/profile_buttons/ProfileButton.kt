package ru.marat.feature_profile.presentation.components.profile_buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.marat.core_ui.theme.AppTheme
import ru.marat.core_ui.R as CoreRes

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    data: ProfileButtonData
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Row(
        modifier = Modifier.clip(RoundedCornerShape(2.dp)).clickable(
            indication = ripple(
                bounded = true,
                radius = screenWidth.dp,
                color = AppTheme.colorScheme.ripple
            ),
            interactionSource = remember { MutableInteractionSource() },
            onClick = data.action
        ).then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(data.icon),
            tint = AppTheme.colorScheme.profileText,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = data.text,
            color = AppTheme.colorScheme.profileText,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ProfileButtonPreview() {
    ProfileButton(
        modifier = Modifier.fillMaxWidth(),
        data = ProfileButtonData(
            text = "История",
            icon = CoreRes.drawable.ic_history,
            action = {  }
        )
    )
}