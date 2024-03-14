package ch.app.hk.bank.locator.core.design.ui.text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
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
                var value by rememberSaveable { mutableStateOf("") }
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
                    value = value,
                    onValueChange = { value = it },
                    placeholder = "test placeholder",
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
                var value by rememberSaveable { mutableStateOf("") }
                AppTextField(
                    modifier =
                        Modifier.semantics {
                            contentDescription = textFieldContentDescription
                        },
                    value = value,
                    onValueChange = { value = it },
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
}
