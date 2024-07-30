package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.result.ResultLayout
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun DeviceNoGpsResult(modifier: Modifier = Modifier) =
    ResultLayout(
        modifier = modifier,
        icon = ImageVector.vectorResource(id = R.drawable.home_ic_location_disabled),
        message = stringResource(id = R.string.home_label_nearby_device_no_gps),
    )

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
private fun DeviceNoGpsPreview() {
    AppContent {
        DeviceNoGpsResult()
    }
}
