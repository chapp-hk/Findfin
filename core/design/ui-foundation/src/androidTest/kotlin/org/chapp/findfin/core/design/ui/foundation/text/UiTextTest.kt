package org.chapp.findfin.core.design.ui.foundation.text

import androidx.compose.ui.test.junit4.createComposeRule
import org.chapp.findfin.core.design.ui.foundation.test.R
import org.junit.Rule
import org.junit.Test

class UiTextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testActualText() {
        composeTestRule.setContent {
            val actualText = UiText.ActualString(value = "test")
            assert(actualText.asString() == "test")
        }
    }

    @Test
    fun testResourceText() {
        composeTestRule.setContent {
            val resourceText = UiText.ResourceString(resId = R.string.core_ui_text_test_no_args)
            assert(resourceText.asString() == "This is a test string")
        }
    }

    @Test
    fun testResourceTextWithArgs() {
        composeTestRule.setContent {
            val resourceText = UiText.ResourceString(resId = R.string.core_ui_text_test_with_args)
            assert(resourceText.asString("world", 2) == "Hello world, this class have 2 tests.")
        }
    }
}
