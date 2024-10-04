package org.chapp.findfin.core.design.ui.modifier

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class ModifierKtxTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contentDescription_isDisplayedCorrectly() {
        composeTestRule.setContent {
            Text(
                modifier = Modifier.contentDescription("test content description"),
                text = "test modifier",
            )
        }

        composeTestRule
            .onNodeWithContentDescription("test content description")
            .assertIsDisplayed()
    }
}
