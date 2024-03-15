package ch.app.hk.bank.locator.feature.onboarding.ui.language.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SelectLanguageErrorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyRetryInvoke() {
        val mockRetry = mockk<() -> Unit>()
        every { mockRetry() } just Runs

        composeTestRule.setContent {
            AppContent {
                SelectLanguageError(
                    onRetry = mockRetry,
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_retry))
            .performClick()

        verify { mockRetry() }
    }
}
