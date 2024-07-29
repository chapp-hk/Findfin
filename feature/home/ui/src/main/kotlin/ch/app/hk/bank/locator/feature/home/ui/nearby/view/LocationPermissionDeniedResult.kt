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
import ch.app.hk.bank.locator.core.location.launcher.permission.rememberLocationPermissionLauncher
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun LocationPermissionDeniedResult(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
) {
    val launcher =
        rememberLocationPermissionLauncher { isPermissionGranted ->
            if (isPermissionGranted) {
                onPermissionGranted()
            }
        }

    LocationPermissionDenied(
        modifier = modifier,
        onPermissionGranted = { launcher.launch(Unit) },
    )
}

@Composable
private fun LocationPermissionDenied(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
) = ResultLayout(
    modifier = modifier,
    icon = ImageVector.vectorResource(id = R.drawable.home_ic_location_denied),
    message = stringResource(id = R.string.home_label_nearby_location_permission_denied),
    buttonText = stringResource(id = R.string.home_button_app_settings),
    onActionButtonClick = onPermissionGranted,
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
private fun LocationPermissionDeniedPreview() {
    AppContent {
        LocationPermissionDenied(onPermissionGranted = {})
    }
}
