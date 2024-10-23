package org.chapp.findfin.feature.onboarding.presentation.ui.language.view

import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.junit.Rule
import org.junit.Test

class SelectLanguageContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val languageListContentDescription =
        context.getString(R.string.onboarding_content_description_language_list)

    @Test
    fun assertAvailableLanguagesItemsDisplayed() {
        composeTestRule.setContent {
            AppContent {
                SelectLanguageContent(
                    availableLanguages =
                        listOf(
                            SelectLanguageUiModel(
                                displayName = "English",
                                tag = "en",
                            ),
                            SelectLanguageUiModel(
                                displayName = "Chinese",
                                tag = "zh",
                            ),
                        ),
                    onLanguageSelect = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(languageListContentDescription)
            .onChildAt(0)
            .assertTextEquals("English")

        composeTestRule
            .onNodeWithContentDescription(languageListContentDescription)
            .onChildAt(1)
            .assertTextEquals("Chinese")
    }

    @Test
    fun verifyOnLanguageSelectInvoke() {
        val mockOnLanguageSelect = mockk<(String) -> Unit>()
        every { mockOnLanguageSelect(any()) } just Runs

        composeTestRule.setContent {
            AppContent {
                SelectLanguageContent(
                    availableLanguages =
                        listOf(
                            SelectLanguageUiModel(
                                displayName = "English",
                                tag = "en",
                            ),
                        ),
                    onLanguageSelect = mockOnLanguageSelect,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(languageListContentDescription)
            .onChildAt(0)
            .performClick()

        verify { mockOnLanguageSelect("en") }
    }
}
