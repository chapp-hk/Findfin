package ch.app.hk.bank.locator.feature.auth.ui.register.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.feature.auth.ui.R

@Composable
internal fun AuthRegisterForm(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onSkip: () -> Unit,
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {},
                actions = {
                    val skipText = stringResource(id = R.string.auth_button_skip)
                    TextButton(
                        modifier = Modifier.semantics { contentDescription = skipText },
                        onClick = onSkip,
                    ) {
                        Text(text = skipText)
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        paddingValues.calculateTopPadding()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthRegisterFormPreviewDayMode() {
    AppTheme {
        AuthRegisterForm {}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthRegisterFormPreviewNightMode() {
    AppTheme {
        AuthRegisterForm {}
    }
}
