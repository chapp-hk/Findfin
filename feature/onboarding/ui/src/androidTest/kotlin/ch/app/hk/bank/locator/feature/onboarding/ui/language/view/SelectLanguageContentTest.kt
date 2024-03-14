package ch.app.hk.bank.locator.feature.onboarding.ui.language.view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SelectLanguageContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

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
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST)
            .onChildAt(0)
            .assertTextEquals("English")

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST)
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
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST)
            .onChildAt(0)
            .performClick()

        verify { mockOnLanguageSelect("en") }
    }
}
