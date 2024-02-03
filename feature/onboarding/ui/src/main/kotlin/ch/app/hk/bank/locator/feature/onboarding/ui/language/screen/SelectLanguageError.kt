package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.feature.onboarding.ui.R

@Composable
fun SelectLanguageError(
    retry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_error_message),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall,
        )

        OutlinedButton(
            modifier = Modifier
                .padding(top = 8.dp),
            onClick = retry,
        ) {
            Text(text = stringResource(id = R.string.onboarding_retry_button))
        }
    }
}
