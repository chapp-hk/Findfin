package ch.app.hk.bank.locator.feature.auth.ui.login.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.core.design.ui.text.rememberAppTextFieldState
import ch.app.hk.bank.locator.feature.auth.ui.R

@Composable
fun AuthLogin(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onAuthorized: () -> Unit,
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
        onBack = onBack,
        onLogin = {
            focusManager.clearFocus(force = true)
        },
    )
}
