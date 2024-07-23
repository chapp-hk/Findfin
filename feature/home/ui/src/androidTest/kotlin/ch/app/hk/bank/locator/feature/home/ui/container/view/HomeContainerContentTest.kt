package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeContainerContentTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val nearByViewModel = mockk<NearByViewModel>()

    @Test
    fun homeContainerContent_displaysAppSearchBarAndFinding() {
        // Set up the mock function
        val onSearch: (String) -> Unit = {}
        every { nearByViewModel.uiState } returns
            MutableStateFlow(ScreenState.Error(NearByUiState.Error(NearByError.GPS_IS_OFF)))

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            HomeContainerContent(
                nearByViewModel = nearByViewModel,
                onSearch = onSearch,
            )
        }

        // Check if AppSearchBar and Finding are displayed
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_search))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_finding))
            .assertIsDisplayed()
    }
}
