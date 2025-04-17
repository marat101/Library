package ru.marat.feature_auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import ru.marat.core_ui.R
import ru.marat.core_ui.theme.AppTheme

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    isLogin: Boolean
) {
    val textFieldState = viewModel.state.collectAsState(Dispatchers.Main.immediate)
    val state = viewModel.state.collectAsState()

    val codeFocusRequester = remember { FocusRequester() }
    val focus = LocalFocusManager.current


    BackHandler(state.value.internalScreen == InternalScreen.Registration) {
        viewModel.onNavigateToLoginClick()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLogin) Image(
            modifier = Modifier
                .size(150.dp)
                .background(Color.White, CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
                .padding(25.dp),
            painter = painterResource(R.drawable.ic_app),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 25.dp),
            text = if (isLogin) "Вход" else "Регистрация", //todo res
            color = AppTheme.colorScheme.profileText,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = textFieldState.value.byScreenType(isLogin).email,
            isError = state.value.byScreenType(isLogin).emailError != null,
            label = { Text("Email") },
            maxLines = 1,
            singleLine = true,
            onValueChange = {
                viewModel.editEmail(it, isLogin)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (!state.value.byScreenType(isLogin).showCodeField) {
                        viewModel.onSendCodeClick(isLogin)
                        focus.clearFocus()
                    } else {
                        codeFocusRequester.requestFocus()
                    }
                }
            )
        )
        AnimatedVisibility(
            visible = state.value.byScreenType(isLogin).showCodeField
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .focusRequester(codeFocusRequester),
                value = textFieldState.value.byScreenType(isLogin).confirmationCode,
                label = { Text("Код подтверждения") },
                isError = state.value.byScreenType(isLogin).codeError != null,
                maxLines = 1,
                singleLine = true,
                onValueChange = {
                    viewModel.editCode(it, isLogin)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (isLogin)
                            viewModel.onLoginConfirmClick()
                        else
                            viewModel.onRegistrationConfirmClick()
                    }
                )
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                if (!state.value.byScreenType(isLogin).showCodeField) {
                    viewModel.onSendCodeClick(isLogin)
                    return@Button
                }

                if (isLogin)
                    viewModel.onLoginConfirmClick()
                else
                    viewModel.onRegistrationConfirmClick()

            }) {
            if (state.value.byScreenType(isLogin).showEmailLoading ||
                state.value.byScreenType(isLogin).showConfirmLoading
            ) CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                color = Color.White
            )
            else Text(
                if (state.value.byScreenType(isLogin).showCodeField) "Подтвердить"
                else "Отправить код",
                color = Color.White
            )
        }
        if (isLogin)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val fontSize = 13.sp
                Text(
                    text = "Нет аккаунта? ",
                    fontSize = fontSize,
                    color = AppTheme.colorScheme.profileText.copy(alpha = 0.8f)
                )
                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            viewModel.onNavigateToRegistrationClick()
                        }
                    ),
                    text = "Зарегистрироваться.",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = fontSize
                )
            }

        Spacer(Modifier.height(100.dp))
    }
}

private fun AuthState.byScreenType(isLogin: Boolean): InternalState =
    if (isLogin) loginState else registrationState