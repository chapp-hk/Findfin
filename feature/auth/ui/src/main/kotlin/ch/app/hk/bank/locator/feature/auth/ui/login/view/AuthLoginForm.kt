package ch.app.hk.bank.locator.feature.auth.ui.login.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.text.AppTextField
import ch.app.hk.bank.locator.core.design.ui.text.AppTextFieldState
import ch.app.hk.bank.locator.core.design.ui.text.PasswordTextField
import ch.app.hk.bank.locator.core.design.ui.text.rememberAppTextFieldState
import ch.app.hk.bank.locator.feature.auth.ui.R

@Composable
internal fun AuthLoginForm(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    emailState: AppTextFieldState,
    passwordState: AppTextFieldState,
    onClose: () -> Unit = {},
    onLogin: () -> Unit = {},
    onDontHaveAccount: () -> Unit = {},
) = Scaffold(
    topBar = {
        @OptIn(ExperimentalMaterial3Api::class)
        TopAppBar(
            modifier = Modifier,
            title = { Text(text = stringResource(id = R.string.auth_title_login)) },
            navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                    )
                }
            },
        )
    },
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
) { paddingValues ->
    Column(
        modifier =
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
    ) {
        val emailContentDescription = stringResource(id = R.string.auth_placeholder_email)
        val passwordContentDescription = stringResource(id = R.string.auth_placeholder_password)

        AppTextField(
            modifier =
                Modifier
                    .semantics { contentDescription = emailContentDescription }
                    .fillMaxWidth(),
            state = emailState,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        PasswordTextField(
            modifier =
                Modifier
                    .semantics { contentDescription = passwordContentDescription }
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            state = passwordState,
        )

        Button(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            onClick = { onLogin() },
            enabled = emailState.value.isNotEmpty() && passwordState.value.isNotEmpty(),
        ) {
            Text(text = stringResource(id = R.string.auth_button_login))
        }

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onDontHaveAccount() },
        ) {
            Text(text = stringResource(id = R.string.auth_button_dont_have_account))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthRegisterFormPreviewDayMode() {
    AppContent {
        AuthLoginForm(
            emailState = rememberAppTextFieldState(),
            passwordState = rememberAppTextFieldState(),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthRegisterFormPreviewNightMode() {
    AppContent {
        AuthLoginForm(
            emailState = rememberAppTextFieldState(),
            passwordState = rememberAppTextFieldState(),
        )
    }
}
