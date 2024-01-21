package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.feature.onboarding.ui.R

@Composable
fun OnboardScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                ),
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_title_language),
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                text = "English",
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = "中文",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
