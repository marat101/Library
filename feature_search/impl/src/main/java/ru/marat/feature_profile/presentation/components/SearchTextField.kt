package ru.marat.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.marat.core_ui.R
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Поиск",
    fontSize: TextUnit = 16.sp,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = fontSize,
            color = AppTheme.colorScheme.profileText
        ),
        maxLines = 1,
        decorationBox = { field ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = AppTheme.colorScheme.profileText
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)){
                    field()
                    if (value.isBlank())
                        Text(
                            text = placeholder,
                            fontSize = fontSize,
                            color = AppTheme.colorScheme.profileText
                        )
                }
            }
        }
    )
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    val text = remember { mutableStateOf("") }
    LibraryTheme(
        false
    ) {
        SearchTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(45.dp),
            value = text.value,
            onValueChange = {
                text.value = it
            }
        )
    }
}

