package ch.app.hk.bank.locator.feature.auth.ui.login.view

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
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginError
import ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel.AuthLoginViewModel
import ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel.AuthLoginViewModelImpl

@Composable
fun AuthLogin(
    modifier: Modifier = Modifier,
    authLoginViewModel: AuthLoginViewModel = hiltViewModel<AuthLoginViewModelImpl>(),
    onClose: () -> Unit = {},
    onAuthorized: () -> Unit = {},
    onDontHaveAccount: () -> Unit = {},
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

    AuthLoginForm(
        snackbarHostState = snackbarHostState,
        emailState = emailState,
        passwordState = passwordState,
        onClose = onClose,
        onLogin = {
            focusManager.clearFocus(force = true)
            authLoginViewModel.emailPasswordLogin(
                email = emailState.value,
                password = passwordState.value,
            )
        },
        onDontHaveAccount = onDontHaveAccount,
    )

    ScreenStateView(
        state = authLoginViewModel.uiState.collectAsStateWithLifecycle(),
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
                LoginError.UNKNOWN -> {
                    val message = stringResource(id = R.string.auth_error_message)
                    LaunchedEffect(error.reason) {
                        snackbarHostState.showSnackbar(message)
                    }
                }

                LoginError.INVALID_CREDENTIAL -> {
                    emailState.setErrorText(
                        errorText = stringResource(id = R.string.auth_error_message_invalid_credential),
                    )
                }

                LoginError.ACCOUNT_DISABLED -> {
                    emailState.setErrorText(
                        errorText = stringResource(id = R.string.auth_error_message_account_disabled),
                    )
                }

                LoginError.TOO_MANY_REQUEST -> {
                    val message = stringResource(id = R.string.auth_error_message_too_many_request)
                    LaunchedEffect(error.reason) {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        },
        success = {
            onAuthorized()
        },
    )
}
