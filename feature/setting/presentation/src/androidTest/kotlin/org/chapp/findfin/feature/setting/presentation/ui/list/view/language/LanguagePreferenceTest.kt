package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.feature.setting.presentation.R
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguagePreferenceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val languagePreferenceViewModel = mockk<LanguagePreferenceViewModel>()

    @Before
    fun setup() {
        mockkObject(BuildVersion)
    }

    @After
    fun tearDown() {
        unmockkObject(BuildVersion)
    }

    @Test
    fun testLanguagePreference_systemPicker() {
        every { BuildVersion.isSupportSystemLanguagePicker } returns true
        every { languagePreferenceViewModel.getCurrentLanguageName() } returns
            UiText.ActualString("English")

        composeTestRule.setContent {
            LanguagePreference(
                languagePreferenceViewModel,
            )
        }

        composeTestRule
            .onNodeWithText("English").assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.setting_description_system_language_picker))
            .assertIsDisplayed()
    }

    @Test
    fun testLanguagePreference_inAppPicker() {
        every { BuildVersion.isSupportSystemLanguagePicker } returns false
        every { languagePreferenceViewModel.getCurrentLanguageTag() } returns "en"
        every { languagePreferenceViewModel.availableLanguages } returns
            listOf(
                LanguagePreferenceItem(
                    title = UiText.ActualString("System default"),
                    value = "",
                ),
                LanguagePreferenceItem(
                    title = UiText.ActualString("English"),
                    value = "en",
                ),
                LanguagePreferenceItem(
                    title = UiText.ActualString("Bahasa Indonesia"),
                    value = "id",
                ),
            )

        composeTestRule.setContent {
            LanguagePreference(
                languagePreferenceViewModel,
            )
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.setting_description_inapp_language_picker))
            .assertIsDisplayed()
    }
}
