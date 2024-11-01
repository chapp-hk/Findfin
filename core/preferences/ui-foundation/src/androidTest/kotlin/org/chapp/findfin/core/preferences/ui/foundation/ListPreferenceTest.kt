package org.chapp.findfin.core.preferences.ui.foundation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ListPreferenceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListPreferenceDialogOpens() {
        val title = "Select Item"
        val items =
            listOf(
                ListPreferenceItem(
                    title = "Title 1",
                    value = "item1",
                ),
                ListPreferenceItem(
                    title = "Title 2",
                    value = "item2",
                ),
            )

        composeTestRule.setContent {
            ListPreference(
                title = title,
                list = items,
                selectedValue = { "item1" },
                onChange = {},
            )
        }

        // Click on the ListPreference to open the dialog
        composeTestRule.onNodeWithText(title).performClick()

        // Check if the dialog is displayed with all items
        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Title 1")),
            useUnmergedTree = true,
        ).assertIsDisplayed()

        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Title 2")),
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun testListPreferenceValueUpdates() {
        // mock selected value
        var selectedValue = "item1"

        val title = "Select Item"
        val items =
            listOf(
                ListPreferenceItem(
                    title = "Title 1",
                    value = "item1",
                ),
                ListPreferenceItem(
                    title = "Title 2",
                    value = "item2",
                ),
            )

        composeTestRule.setContent {
            ListPreference(
                title = title,
                list = items,
                selectedValue = { selectedValue },
                onChange = { selectedValue = it },
            )
        }

        // Check if stored value shows
        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()

        // Click on the ListPreference to open the dialog
        composeTestRule.onNodeWithText(title).performClick()

        // Click on dialog item
        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Title 1")),
            useUnmergedTree = true,
        ).performClick()

        // Check if value updated
        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
    }
}
