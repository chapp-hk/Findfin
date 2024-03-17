package ch.app.hk.bank.locator.feature.onboarding.ui.language.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModelImpl
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SelectLanguageScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val selectLanguageViewModel = mockk<SelectLanguageViewModelImpl>(relaxed = true)

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testLoadingShown() {
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    goToRequestPermission = {},
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
            MutableStateFlow(ScreenState.Error(SelectLanguageUiState("en")))

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    goToRequestPermission = {},
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
            listOf(mockk(relaxed = true))
        every { selectLanguageViewModel.uiState } returns
            MutableStateFlow(ScreenState.Empty)

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    goToRequestPermission = {},
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
            MutableStateFlow(ScreenState.Success(SelectLanguageUiState("en")))

        composeTestRule.setContent {
            AppContent {
                SelectLanguageScreen(
                    goToRequestPermission = mockGoToRequestPermission,
                )
            }
        }

        verify { mockGoToRequestPermission() }
    }
}
