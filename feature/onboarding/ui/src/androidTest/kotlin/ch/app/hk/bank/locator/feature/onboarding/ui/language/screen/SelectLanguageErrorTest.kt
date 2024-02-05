package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.theme.AppTheme
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
            AppTheme {
                SelectLanguageError(
                    retry = mockRetry
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_ERROR_RETRY)
            .performClick()

        verify { mockRetry() }
    }
}
