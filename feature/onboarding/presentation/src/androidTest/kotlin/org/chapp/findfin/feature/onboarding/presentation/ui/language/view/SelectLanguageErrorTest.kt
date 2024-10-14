package org.chapp.findfin.feature.onboarding.presentation.ui.language.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.testing.instrument.getResourceString
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
