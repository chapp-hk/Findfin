package org.chapp.findfin.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.result.ResultLayout
import org.chapp.findfin.feature.home.ui.R

@Composable
internal fun LocationDisabled(
    modifier: Modifier = Modifier,
    onRequestEnableLocation: () -> Unit,
) = ResultLayout(
    modifier = modifier,
    icon = ImageVector.vectorResource(id = R.drawable.home_ic_location_disabled),
    message = stringResource(id = R.string.home_label_nearby_location_disabled),
    buttonText = stringResource(id = R.string.home_button_turn_on_location),
    onActionButtonClick = onRequestEnableLocation,
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
private fun NearByLocationDisabledPreview() {
    AppContent {
        LocationDisabled(onRequestEnableLocation = {})
    }
}
