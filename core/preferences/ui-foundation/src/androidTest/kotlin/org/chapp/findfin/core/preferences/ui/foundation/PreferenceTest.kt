package org.chapp.findfin.core.preferences.ui.foundation

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import org.junit.Rule
import org.junit.Test

class PreferenceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPreference_WithBothTitleAndDescription() {
        composeTestRule.setContent {
            Preference(
                title = "Title",
                description = "Description",
            )
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
    }

    @Test
    fun testPreference_WithOnlyTitle() {
        composeTestRule.setContent {
            Preference(title = "Title")
        }

        composeTestRule.onNodeWithText("Title").onParent().assert(
            SemanticsMatcher(description = "assert Preference has only one child") {
                it.children.size == 1
            },
        )
    }
}
