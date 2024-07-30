package ch.app.hk.bank.locator.core.design.ui.result

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
