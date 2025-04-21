package ru.marat.feature_book.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.marat.core_ui.theme.AppTheme
import ru.marat.library.ui.theme.LibraryTheme

@Composable
fun BookDescription(
    modifier: Modifier,
    text: String
) {
    Column(modifier) {
        Text(
            text = "Описание", //todo res
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.colorScheme.profileText
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            color = AppTheme.colorScheme.settingsCategory,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun BookDescriptionPreview() {
    LibraryTheme(
        false
    ) {
        BookDescription(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            text = "Меньше 1% населения Земли владеют 46% мирового капитала. А бедных на планете аж 70%! Шокирует, не так ли? Почему так происходит? Вы наверняка не раз задавались вопросом: «Откуда богачи берут деньги, и почему я так не могу?» Эта книга дает исчерпывающее пояснение. Алексей Марков просто и увлекательно рассказывает о мировой денежной несправедливости."
        )
    }
}