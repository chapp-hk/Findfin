package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.location.launcher.setting.rememberLocationSourceSettingsLauncher
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun NearByLocationDisabledComponent(onLocationServiceEnabled: () -> Unit) {
    val launcher =
        rememberLocationSourceSettingsLauncher { isLocationEnabled ->
            if (isLocationEnabled) {
                onLocationServiceEnabled()
            }
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.home_ic_location_disabled),
            contentDescription = "",
        )

        OutlinedButton(
            modifier = Modifier,
            onClick = { launcher.launch(Unit) },
        ) {
            Text(text = "Go settings")
        }
    }
}
