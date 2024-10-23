package org.chapp.findfin.core.design.ui.foundation.result

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a result layout with an icon, message, and an optional action button.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param icon The icon to be displayed in the layout.
 * @param message The message to be displayed in the layout.
 * @param buttonText The text to be displayed on the optional action button. If null, the button is not displayed.
 * @param onActionButtonClick The callback to be invoked when the action button is clicked. If null, the button is not displayed.
 */
@Composable
fun ResultLayout(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    message: String,
    buttonText: String? = null,
    onActionButtonClick: (() -> Unit)? = null,
) = Column(
    modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Icon(
        modifier = Modifier.size(60.dp),
        imageVector = icon,
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
        text = message,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.outline,
        textAlign = TextAlign.Center,
    )

    if (buttonText != null && onActionButtonClick != null) {
        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onActionButtonClick,
        ) {
            Text(text = buttonText)
        }
    }
}
