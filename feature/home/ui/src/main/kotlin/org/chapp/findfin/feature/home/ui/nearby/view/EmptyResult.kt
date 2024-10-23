package org.chapp.findfin.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.core.design.ui.foundation.result.ResultLayout
import org.chapp.findfin.feature.home.ui.R

@Composable
internal fun EmptyResult(modifier: Modifier = Modifier) =
    ResultLayout(
        modifier = modifier,
        icon = ImageVector.vectorResource(id = R.drawable.home_ic_exclamation),
        message = stringResource(id = R.string.home_label_nearby_no_services),
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
private fun EmptyResultPreview() {
    AppContent {
        EmptyResult()
    }
}
