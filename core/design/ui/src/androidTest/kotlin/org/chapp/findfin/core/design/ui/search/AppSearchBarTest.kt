package org.chapp.findfin.core.design.ui.search

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.chapp.findfin.core.design.ui.AppContent
import org.junit.Rule
import org.junit.Test

class AppSearchBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val searchBarContentDescription = "AppSearchBar"

    @Test
    fun assertPlaceholderIsDisplayed() {
        composeTestRule.setContent {
            AppContent {
                val state = rememberAppSearchBarState(placeholder = "test placeholder")
                AppSearchBar(
                    modifier = Modifier.testTag(searchBarContentDescription),
                    state = state,
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = "test placeholder",
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
    }
}
