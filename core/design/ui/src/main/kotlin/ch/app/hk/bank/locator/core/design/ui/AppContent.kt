package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.core.design.theme.DarkColors
import ch.app.hk.bank.locator.core.design.theme.LightColors
import ch.app.hk.bank.locator.core.design.theme.Shapes
import ch.app.hk.bank.locator.core.design.theme.Typography

@Composable
fun AppContent(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors =
        if (!useDarkTheme) {
            LightColors
        } else {
            DarkColors
        }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = Typography,
        content = content,
    )
}
