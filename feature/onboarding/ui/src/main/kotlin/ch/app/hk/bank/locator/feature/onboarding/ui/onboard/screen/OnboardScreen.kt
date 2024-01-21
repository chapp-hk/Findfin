package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                )
                .padding(
                    top = 55.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_title_language),
                style = MaterialTheme.typography.headlineSmall,
            )

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                onClick = {},
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .wrapContentHeight(),
                    text = "English",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {},
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .wrapContentHeight(),
                    text = "中文",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
