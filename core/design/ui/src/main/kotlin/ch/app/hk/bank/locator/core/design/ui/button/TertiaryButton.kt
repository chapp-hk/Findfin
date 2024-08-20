package ch.app.hk.bank.locator.core.design.ui.button

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.ui.AppContent

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) = TextButton(
    modifier = modifier,
    onClick = onClick,
) {
    Text(text = text)
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
private fun TertiaryButtonPreview() {
    AppContent {
        TertiaryButton(text = "Tertiary Button", onClick = {})
    }
}
