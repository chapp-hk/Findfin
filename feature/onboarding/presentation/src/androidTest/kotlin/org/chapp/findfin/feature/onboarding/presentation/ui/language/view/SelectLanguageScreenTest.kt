package org.chapp.findfin.feature.onboarding.presentation.ui.language.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel.SelectLanguageViewModel
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class SelectLanguageScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val selectLanguageViewModel = mockk<SelectLanguageViewModel>(relaxed = true)

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testLoadingShown() {
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(SelectLanguageUiState.Loading)

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    onFinishSelectLanguage = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.onboarding_content_description_loading))
            .assertIsDisplayed()
    }

    @Test
    fun testErrorShown() {
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(SelectLanguageUiState.Error(selectedLanguageTag = "en"))

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    onFinishSelectLanguage = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_error_message))
            .assertIsDisplayed()
    }

    @Test
    fun testContentShown() {
        every { selectLanguageViewModel.availableLanguages } returns
            listOf(
                SelectLanguageUiModel(
                    displayName = UiText.ActualString(value = "English"),
                    tag = "en",
                ),
                SelectLanguageUiModel(
                    displayName = UiText.ActualString(value = "Japanese"),
                    tag = "jp",
                ),
            )
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(SelectLanguageUiState.Initial)

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    onFinishSelectLanguage = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.onboarding_content_description_language_list))
            .assertIsDisplayed()
    }

    @Test
    fun testGoToRequestPermission() {
        val mockGoToRequestPermission = mockk<() -> Unit>()
        every { mockGoToRequestPermission() } just Runs

        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(SelectLanguageUiState.Success(selectedLanguageTag = "en"))

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    onFinishSelectLanguage = mockGoToRequestPermission,
                )
            }
        }

        verify { mockGoToRequestPermission() }
    }
}
