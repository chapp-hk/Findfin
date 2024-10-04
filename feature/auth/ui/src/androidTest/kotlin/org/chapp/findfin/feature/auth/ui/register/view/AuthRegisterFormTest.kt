package org.chapp.findfin.feature.auth.ui.register.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.text.rememberAppTextFieldState
import org.chapp.findfin.feature.auth.ui.R
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class AuthRegisterFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyOnCloseInvoked() {
        val mockedOnClose = mockk<() -> Unit>()
        every { mockedOnClose() } just Runs

        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                    onClose = mockedOnClose,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_content_description_close))
            .performClick()

        verify { mockedOnClose() }
    }

    @Test
    fun verifyOnRegisterInvoked() {
        val mockedOnRegister = mockk<() -> Unit>()
        every { mockedOnRegister() } just Runs

        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                    onRegister = mockedOnRegister,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_email))
            .performTextInput("email")

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_password))
            .performTextInput("password")

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_register))
            .performClick()

        verify { mockedOnRegister() }
    }

    @Test
    fun verifyOnHaveAccountInvoked() {
        val mockedOnHaveAccount = mockk<() -> Unit>()
        every { mockedOnHaveAccount() } just Runs

        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                    onHaveAccount = mockedOnHaveAccount,
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_already_have_account))
            .performClick()

        verify { mockedOnHaveAccount() }
    }

    @Test
    fun assertRegisterButtonIsNotEnabledWhenOnlyEmailInput() {
        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_email))
            .performTextInput("email")

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_register))
            .assertIsNotEnabled()
    }

    @Test
    fun assertRegisterButtonIsNotEnabledWhenOnlyPasswordInput() {
        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_placeholder_password))
            .performTextInput("password")

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_register))
            .assertIsNotEnabled()
    }
}
