package ch.app.hk.bank.locator.feature.auth.ui.login.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.text.rememberAppTextFieldState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class AuthLoginFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assertLoginButtonIsNotEnabledWhenOnlyEmailInput() {
        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthLoginForm(
                    emailState = emailState,
                    passwordState = passwordState,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_email))
            .performTextInput("email")

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_login))
            .assertIsNotEnabled()
    }

    @Test
    fun assertLoginButtonIsNotEnabledWhenOnlyPasswordInput() {
        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthLoginForm(
                    emailState = emailState,
                    passwordState = passwordState,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_password))
            .performTextInput("password")

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_login))
            .assertIsNotEnabled()
    }
}
