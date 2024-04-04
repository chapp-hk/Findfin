package ch.app.hk.bank.locator.core.design.ui.text

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performTextInput
import ch.app.hk.bank.locator.core.design.ui.AppContent
import org.junit.Rule
import org.junit.Test

class AppTextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val textFieldContentDescription = "AppTextField"

    @Test
    fun assertPlaceholderIsDisplayed() {
        composeTestRule.setContent {
            AppContent {
                val state = rememberAppTextFieldState(placeholder = "test placeholder")
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
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
            .onParent()
            .assertContentDescriptionEquals(textFieldContentDescription)
    }

    @Test
    fun assertInputText() {
        composeTestRule.setContent {
            AppContent {
                val state = rememberAppTextFieldState()
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
                    state = state,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .performTextInput("testing input")

        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .assert(hasText("testing input"))
    }

    @Test
    fun assertErrorTextIsNotDisplayedWhenInputNewValue() {
        composeTestRule.setContent {
            val state = rememberAppTextFieldState(isClearErrorWhenInput = true)
            state.setErrorText(errorText = "test supporting text")

            AppContent {
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
                    state = state,
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = "test supporting text",
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(textFieldContentDescription)

        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .performTextInput("testing input")

        composeTestRule
            .onNodeWithText(
                text = "test supporting text",
                useUnmergedTree = true,
            )
            .assertIsNotDisplayed()
    }

    @Test
    fun assertErrorTextIsDisplayedWhenInputNewValue() {
        composeTestRule.setContent {
            val state = rememberAppTextFieldState()
            state.setErrorText(errorText = "test supporting text")

            AppContent {
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
                    state = state,
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = "test supporting text",
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(textFieldContentDescription)

        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .performTextInput("testing input")

        composeTestRule
            .onNodeWithText(
                text = "test supporting text",
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(textFieldContentDescription)
    }
}
