package org.chapp.findfin.feature.auth.presentation.ui.register.view

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.modifier.contentDescription
import org.chapp.findfin.core.design.ui.text.AppTextField
import org.chapp.findfin.core.design.ui.text.AppTextFieldState
import org.chapp.findfin.core.design.ui.text.PasswordTextField
import org.chapp.findfin.core.design.ui.text.rememberAppTextFieldState
import org.chapp.findfin.feature.auth.presentation.R

@Composable
internal fun AuthRegisterForm(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    emailState: AppTextFieldState,
    passwordState: AppTextFieldState,
    onClose: () -> Unit = {},
    onRegister: () -> Unit = {},
    onHaveAccount: () -> Unit = {},
) = Scaffold(
    topBar = {
        @OptIn(ExperimentalMaterial3Api::class)
        TopAppBar(
            modifier = Modifier,
            title = { Text(text = stringResource(id = R.string.auth_title_register)) },
            navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.auth_content_description_close),
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
                    .contentDescription(emailContentDescription)
                    .fillMaxWidth(),
            state = emailState,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        PasswordTextField(
            modifier =
                Modifier
                    .contentDescription(passwordContentDescription)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            state = passwordState,
        )

        Button(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            onClick = { onRegister() },
            enabled = emailState.value.isNotEmpty() && passwordState.value.isNotEmpty(),
        ) {
            Text(text = stringResource(id = R.string.auth_button_register))
        }

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onHaveAccount() },
        ) {
            Text(text = stringResource(id = R.string.auth_button_already_have_account))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthRegisterFormPreview() {
    AppContent {
        AuthRegisterForm(
            emailState = rememberAppTextFieldState(),
            passwordState = rememberAppTextFieldState(),
        )
    }
}
