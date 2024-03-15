package ch.app.hk.bank.locator.core.design.ui.button

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) = OutlinedButton(
    modifier = modifier,
    onClick = onClick,
) {
    Text(text = text)
}
