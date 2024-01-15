package ch.app.hk.bank.locator.feature.onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun LandingScreen(
    back: () -> Unit,
) {
    Scaffold {
        it.calculateBottomPadding()
        Column {
            Button(
                onClick = back,
            ) {
                Text(text = "back")
            }
        }
    }
}
