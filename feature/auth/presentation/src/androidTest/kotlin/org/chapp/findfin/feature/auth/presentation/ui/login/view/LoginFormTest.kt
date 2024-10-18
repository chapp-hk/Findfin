package org.chapp.findfin.feature.auth.presentation.ui.login.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.text.rememberAppTextFieldState
import org.chapp.findfin.feature.auth.presentation.R
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class LoginFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assertLoginButtonIsNotEnabledWhenOnlyEmailInput() {
        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                LoginForm(
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
                LoginForm(
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
