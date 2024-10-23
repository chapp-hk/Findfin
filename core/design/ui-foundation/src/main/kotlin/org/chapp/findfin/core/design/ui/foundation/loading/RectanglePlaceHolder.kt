package org.chapp.findfin.core.design.ui.foundation.loading

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a rectangular placeholder with a shimmer effect.
 *
 * @param modifier The modifier to be applied to the placeholder.
 * @param cornerRadius The radius of the corners of the rectangular placeholder.
 * @param width The width of the rectangular placeholder.
 * @param height The height of the rectangular placeholder.
 */
@Composable
fun RectanglePlaceHolder(
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    width: Dp,
    height: Dp,
) = Box(
    modifier =
        modifier
            .clip(shape = RoundedCornerShape(cornerRadius))
            .size(width = width, height = height)
            .shimmer(),
)

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = android.graphics.Color.BLACK.toLong(),
)
@Composable
private fun RectanglePlaceHolderPreview() {
    RectanglePlaceHolder(
        cornerRadius = 10.dp,
        width = 100.dp,
        height = 50.dp,
    )
}
