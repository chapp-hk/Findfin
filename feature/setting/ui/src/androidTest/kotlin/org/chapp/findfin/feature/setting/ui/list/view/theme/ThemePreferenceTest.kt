package org.chapp.findfin.feature.setting.ui.list.view.theme

import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.chapp.findfin.feature.setting.ui.R
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class ThemePreferenceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val themePreferenceViewModel = mockk<ThemePreferenceViewModel>(relaxUnitFun = true)

    @Test
    fun testThemePreference() {
        val mockedFlow = flowOf("LIGHT")
        every { themePreferenceViewModel.getCurrentTheme() } returns mockedFlow

        composeTestRule.setContent {
            ThemePreference(themePreferenceViewModel)
        }

        // click on theme preference
        composeTestRule
            .onNodeWithText(getResourceString(R.string.setting_theme_title))
            .performClick()

        // click on dialog item
        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Dark")),
            useUnmergedTree = true,
        ).performClick()

        coVerify { themePreferenceViewModel.setTheme(any()) }
    }
}
