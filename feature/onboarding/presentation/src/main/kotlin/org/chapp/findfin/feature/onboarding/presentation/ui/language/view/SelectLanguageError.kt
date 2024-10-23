package org.chapp.findfin.feature.onboarding.presentation.ui.language.view

import android.content.res.Configuration
import android.graphics.Color
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.feature.onboarding.presentation.R

@Composable
internal fun SelectLanguageError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            modifier
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
            modifier = Modifier.padding(top = 8.dp),
            onClick = onRetry,
        ) {
            Text(text = stringResource(id = R.string.onboarding_button_retry))
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SelectLanguageErrorPreview() {
    AppContent {
        SelectLanguageError(onRetry = {})
    }
}
