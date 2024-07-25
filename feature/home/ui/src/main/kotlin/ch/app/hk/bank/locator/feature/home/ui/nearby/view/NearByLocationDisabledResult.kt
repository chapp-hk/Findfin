package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.location.launcher.setting.rememberLocationSourceSettingsLauncher
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun NearByLocationDisabledResult(
    modifier: Modifier = Modifier,
    onLocationServiceEnabled: () -> Unit,
) {
    val launcher =
        rememberLocationSourceSettingsLauncher { isLocationEnabled ->
            if (isLocationEnabled) {
                onLocationServiceEnabled()
            }
        }

    NearByLocationDisabled(
        modifier = modifier,
        onActionButtonClick = { launcher.launch(Unit) },
    )
}

@Composable
private fun NearByLocationDisabled(
    modifier: Modifier = Modifier,
    onActionButtonClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.home_ic_location_disabled),
            tint = MaterialTheme.colorScheme.outline,
            contentDescription = "",
        )

        Text(
            modifier =
                Modifier.padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 24.dp,
                ),
            text = "Please enable device location service to find nearby services",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
        )

        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onActionButtonClick,
        ) {
            Text(text = "Go settings")
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun NearByLocationDisabledPreview() {
    AppContent {
        NearByLocationDisabled(onActionButtonClick = {})
    }
}
