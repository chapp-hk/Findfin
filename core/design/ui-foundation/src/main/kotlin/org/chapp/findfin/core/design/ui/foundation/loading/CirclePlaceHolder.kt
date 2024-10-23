package org.chapp.findfin.core.design.ui.foundation.loading

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a circular placeholder with a shimmer effect.
 *
 * @param modifier The modifier to be applied to the placeholder.
 * @param size The size of the circular placeholder.
 */
@Composable
fun CirclePlaceHolder(
    modifier: Modifier = Modifier,
    size: Dp,
) = Box(
    modifier =
        modifier
            .clip(shape = CircleShape)
            .size(size)
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
private fun CirclePlaceHolderPreview() {
    CirclePlaceHolder(
        size = 50.dp,
    )
}
