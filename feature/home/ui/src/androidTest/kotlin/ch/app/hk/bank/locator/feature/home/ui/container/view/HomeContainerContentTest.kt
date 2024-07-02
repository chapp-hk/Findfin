package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class HomeContainerContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeContainerContent_displaysAppSearchBarAndFinding() {
        // Set up the mock function
        val onSearch: (String) -> Unit = {}

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            HomeContainerContent(onSearch = onSearch)
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
