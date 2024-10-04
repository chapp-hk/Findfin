package org.chapp.findfin.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.result.ResultLayout
import org.chapp.findfin.feature.home.ui.R

@Composable
internal fun LocationPermissionDenied(
    modifier: Modifier = Modifier,
    isPermanentlyDenied: Boolean,
    onRequestPermission: () -> Unit,
) {
    val buttonText =
        if (isPermanentlyDenied) {
            stringResource(id = R.string.home_button_app_setting)
        } else {
            stringResource(id = R.string.home_button_grant_permission)
        }

    ResultLayout(
        modifier = modifier,
        icon = ImageVector.vectorResource(id = R.drawable.home_ic_location_denied),
        message = stringResource(id = R.string.home_label_nearby_location_permission_denied),
        buttonText = buttonText,
        onActionButtonClick = onRequestPermission,
    )
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
private fun LocationPermissionDeniedPreview(
    @PreviewParameter(LocationPermissionDeniedPreviewParameterProvider::class)
    isPermanentlyDenied: Boolean,
) {
    AppContent {
        LocationPermissionDenied(
            isPermanentlyDenied = isPermanentlyDenied,
            onRequestPermission = {},
        )
    }
}

private class LocationPermissionDeniedPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}
