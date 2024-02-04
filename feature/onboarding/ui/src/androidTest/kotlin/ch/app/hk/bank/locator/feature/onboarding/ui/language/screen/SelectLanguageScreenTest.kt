package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class SelectLanguageScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val selectLanguageViewModel = mockk<SelectLanguageViewModel>()

    @Test
    fun testLoadingShown() {
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

        composeTestRule.setContent {
            AppTheme {
                SelectLanguageScreen(
                    selectLanguageViewModel = selectLanguageViewModel,
                    goToRequestPermission = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun testErrorShown() {
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Error(Throwable(), SelectLanguageUiState("en")))

        composeTestRule.setContent {
            AppTheme {
                SelectLanguageScreen(
                    selectLanguageViewModel = selectLanguageViewModel,
                    goToRequestPermission = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_ERROR)
            .assertIsDisplayed()
    }

    @Test
    fun testContentShown() {
        every { selectLanguageViewModel.availableLanguages } returns
            listOf(mockk(relaxed = true))
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Empty)

        composeTestRule.setContent {
            AppTheme {
                SelectLanguageScreen(
                    selectLanguageViewModel = selectLanguageViewModel,
                    goToRequestPermission = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT)
            .assertIsDisplayed()
    }

    @Test
    fun testGoToRequestPermission() {
        val mockGoToRequestPermission = mockk<() -> Unit>()
        every { mockGoToRequestPermission() } just Runs

        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(SelectLanguageUiState("en")))

        composeTestRule.setContent {
            AppTheme {
                SelectLanguageScreen(
                    selectLanguageViewModel = selectLanguageViewModel,
                    goToRequestPermission = mockGoToRequestPermission,
                )
            }
        }

        verify { mockGoToRequestPermission() }
    }
}
