package org.chapp.findfin.core.design.ui.result

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.test.R
import org.junit.Rule
import org.junit.Test

class ResultLayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun resultLayout_displaysButtonWhenButtonTextAndOnClickProvided() {
        composeTestRule.setContent {
            AppContent {
                ResultLayout(
                    icon = ImageVector.vectorResource(id = R.drawable.core_ui_ic_android),
                    message = "Message",
                    buttonText = "Button",
                    onActionButtonClick = { },
                )
            }
        }

        composeTestRule
            .onNodeWithText("Button")
            .assertIsDisplayed()
    }

    @Test
    fun resultLayout_doesNotDisplayButtonWhenOnlyButtonTextProvided() {
        composeTestRule.setContent {
            AppContent {
                ResultLayout(
                    icon = ImageVector.vectorResource(id = R.drawable.core_ui_ic_android),
                    message = "Message",
                    buttonText = "Button",
                )
            }
        }

        composeTestRule
            .onNodeWithText("Button")
            .assertIsNotDisplayed()
    }

    @Test
    fun resultLayout_doesNotDisplayButtonWhenOnlyOnClickProvided() {
        composeTestRule.setContent {
            AppContent {
                ResultLayout(
                    icon = ImageVector.vectorResource(id = R.drawable.core_ui_ic_android),
                    message = "Message",
                    onActionButtonClick = { },
                )
            }
        }

        composeTestRule
            .onNodeWithText("Button")
            .assertIsNotDisplayed()
    }

    @Test
    fun resultLayout_doesNotDisplayButtonWhenNoButtonTextOrOnClickProvided() {
        composeTestRule.setContent {
            AppContent {
                ResultLayout(
                    icon = ImageVector.vectorResource(id = R.drawable.core_ui_ic_android),
                    message = "Message",
                )
            }
        }

        composeTestRule
            .onNodeWithText("Button")
            .assertIsNotDisplayed()
    }
}
