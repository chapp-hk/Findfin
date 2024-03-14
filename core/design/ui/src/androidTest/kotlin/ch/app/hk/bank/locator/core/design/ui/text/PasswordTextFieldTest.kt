package ch.app.hk.bank.locator.core.design.ui.text

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.R
import org.junit.Rule
import org.junit.Test

class PasswordTextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val textFieldContentDescription = "PasswordTextField"

    @Test
    fun testPasswordVisibilityToggle() {
        val password = "pwd123456"
        val maskedPassword =
            password
                .map { PasswordVisualTransformation().mask }
                .joinToString(separator = "")

        composeTestRule.setContent {
            AppContent {
                var value by rememberSaveable { mutableStateOf("") }
                PasswordTextField(
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
            .performTextInput(password)

        // initial assert password is masked
        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .assert(hasText(maskedPassword))

        composeTestRule
            .onNodeWithContentDescription(
                label = context.getString(R.string.core_ui_content_description_visibility_toggle),
                useUnmergedTree = true,
            )
            .performClick()

        // assert password is visible
        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .assert(hasText(password))

        composeTestRule
            .onNodeWithContentDescription(
                label = context.getString(R.string.core_ui_content_description_visibility_toggle),
                useUnmergedTree = true,
            )
            .performClick()

        // assert password is masked again
        composeTestRule
            .onNodeWithContentDescription(textFieldContentDescription)
            .assert(hasText(maskedPassword))
    }
}
