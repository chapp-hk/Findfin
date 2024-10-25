package org.chapp.findfin.core.preferences.ui.foundation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.chapp.findfin.core.preferences.runtime.PreferenceStore
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
                preferenceStore =
                    object : PreferenceStore<String> {
                        override fun get() = flowOf("item1")

                        override suspend fun set(value: String) {}
                    },
            )
        }

        // Click on the ListPreference to open the dialog
        composeTestRule.onNodeWithText(title).performClick()

        // Check if the dialog is displayed with all items
        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Item 1")),
            useUnmergedTree = true,
        ).assertIsDisplayed()

        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Item 2")),
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun testListPreferenceValueUpdates() {
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
                preferenceStore =
                    object : PreferenceStore<String> {
                        private val _flow = MutableStateFlow("item2")

                        override fun get() = _flow

                        override suspend fun set(value: String) {
                            _flow.value = value
                        }
                    },
            )
        }

        // Check if stored value shows
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()

        // Click on the ListPreference to open the dialog
        composeTestRule.onNodeWithText(title).performClick()

        // Click on dialog item
        composeTestRule.onNode(
            matcher = isDialog() and hasAnyDescendant(hasText("Item 1")),
            useUnmergedTree = true,
        ).performClick()

        // Check if value updated
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
    }
}
