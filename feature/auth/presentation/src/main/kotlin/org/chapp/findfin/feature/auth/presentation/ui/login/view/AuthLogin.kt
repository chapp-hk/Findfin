package org.chapp.findfin.feature.auth.presentation.ui.login.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.chapp.findfin.core.design.ui.modifier.contentDescription
import org.chapp.findfin.core.design.ui.text.rememberAppTextFieldState
import org.chapp.findfin.feature.auth.presentation.R
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginError
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginUiState
import org.chapp.findfin.feature.auth.presentation.ui.login.viewmodel.AuthLoginViewModel

@Composable
fun AuthLogin(
    modifier: Modifier = Modifier,
    authLoginViewModel: AuthLoginViewModel = hiltViewModel(),
    onClose: () -> Unit = {},
    onFinishAuth: () -> Unit = {},
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
    val uiState by authLoginViewModel.uiState.collectAsStateWithLifecycle()

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

    when (val state = uiState) {
        LoginUiState.None -> {}
        LoginUiState.Loading -> {
            CircularProgressIndicator(
                modifier =
                    Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {},
                        )
                        .contentDescription(stringResource(id = R.string.auth_content_description_loading))
                        .background(Color.Transparent)
                        .matchParentSize()
                        .wrapContentSize(Alignment.Center),
            )
        }
        LoginUiState.Authorized -> {
            onFinishAuth()
        }
        is LoginUiState.Error -> {
            when (state.reason) {
                LoginError.UNKNOWN -> {
                    val message = stringResource(id = R.string.auth_error_message)
                    LaunchedEffect(state.reason) {
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
                    LaunchedEffect(state.reason) {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
    }
}
