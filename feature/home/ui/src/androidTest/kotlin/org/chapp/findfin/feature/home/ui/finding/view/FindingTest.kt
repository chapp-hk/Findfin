package org.chapp.findfin.feature.home.ui.finding.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.feature.home.ui.R
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class FindingTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun finding_composable_displays_correct_ui_elements() {
        composeTestRule.setContent {
            AppContent {
                Finding()
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_title_finding))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_find_your_bank))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_find_bank_or_atms))
            .assertIsDisplayed()
    }

    @Test
    fun finding_composable_performs_clicks() {
        val onFindYourBank = mockk<() -> Unit>(relaxed = true)
        val onFindBankOrAtms = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            AppContent {
                Finding(
                    onFindYourBank = onFindYourBank,
                    onFindBankOrAtms = onFindBankOrAtms,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_label_find_your_bank))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_label_find_bank_or_atms))
            .performClick()

        verify(exactly = 1) { onFindYourBank() }
        verify(exactly = 1) { onFindBankOrAtms() }
    }
}
