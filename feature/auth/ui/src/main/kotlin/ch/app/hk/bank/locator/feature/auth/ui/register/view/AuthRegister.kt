package ch.app.hk.bank.locator.feature.auth.ui.register.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.core.design.ui.text.rememberAppTextFieldState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterError
import ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel.AuthRegisterViewModel
import ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel.AuthRegisterViewModelImpl

@Composable
fun AuthRegister(
    modifier: Modifier = Modifier,
    authRegisterViewModel: AuthRegisterViewModel = hiltViewModel<AuthRegisterViewModelImpl>(),
    onClose: () -> Unit = {},
    onFinishAuth: () -> Unit,
    onHaveAccount: () -> Unit,
) = Box(modifier = modifier) {
    val emailState =
        rememberAppTextFieldState(
            placeholder = stringResource(id = R.string.auth_placeholder_email),
        )
    val passwordState =
        rememberAppTextFieldState(
            placeholder = stringResource(id = R.string.auth_placeholder_password),
        )
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    AuthRegisterForm(
        snackbarHostState = snackbarHostState,
        emailState = emailState,
        passwordState = passwordState,
        onClose = onClose,
        onRegister = {
            focusManager.clearFocus(force = true)
            authRegisterViewModel.emailPasswordRegister(
                email = emailState.value,
                password = passwordState.value,
            )
        },
        onHaveAccount = onHaveAccount,
    )

    ScreenStateView(
        state = authRegisterViewModel.uiState.collectAsStateWithLifecycle(),
        loading = {
            val description = stringResource(id = R.string.auth_content_description_loading)
            CircularProgressIndicator(
                modifier =
                    Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {},
                        )
                        .semantics { contentDescription = description }
                        .background(Color.Transparent)
                        .matchParentSize()
                        .wrapContentSize(Alignment.Center),
            )
        },
        error = { error ->
            when (error.reason) {
                AuthRegisterError.UNKNOWN -> {
                    val message = stringResource(id = R.string.auth_error_message)
                    LaunchedEffect(error.reason) {
                        snackbarHostState.showSnackbar(message)
                        authRegisterViewModel.resetUiState()
                    }
                }

                AuthRegisterError.INVALID_EMAIL -> {
                    emailState.setErrorText(
                        errorText = stringResource(id = R.string.auth_error_message_invalid_email),
                    )
                }

                AuthRegisterError.WEAK_PASSWORD -> {
                    passwordState.setErrorText(
                        errorText = stringResource(id = R.string.auth_error_message_password_strength),
                    )
                }

                AuthRegisterError.EMAIL_ALREADY_IN_USE -> {
                    emailState.setErrorText(
                        errorText = stringResource(id = R.string.auth_error_message_email_already_in_use),
                    )
                }
            }
        },
        success = {
            onFinishAuth()
        },
    )
}
