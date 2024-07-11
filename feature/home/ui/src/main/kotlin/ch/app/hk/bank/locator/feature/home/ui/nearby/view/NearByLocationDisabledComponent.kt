package ch.app.hk.bank.locator.feature.home.ui.nearby.view

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
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.location.launcher.setting.rememberLocationSourceSettingsLauncher

@Composable
internal fun NearByLocationDisabledComponent() {
    val launcher =
        rememberLocationSourceSettingsLauncher {
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Location service disabled",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall,
        )

        OutlinedButton(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { launcher.launch(Unit) },
        ) {
            Text(text = "Go settings")
        }
    }
}
