package ch.app.hk.bank.locator.feature.auth.ui.register.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.text.rememberAppTextFieldState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class AuthRegisterFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyOnSkipInvoked() {
        val mockedOnSkip = mockk<() -> Unit>()
        every { mockedOnSkip() } just Runs

        composeTestRule.setContent {
            AppContent {
                val emailState = rememberAppTextFieldState()
                val passwordState = rememberAppTextFieldState()
                AuthRegisterForm(
                    emailState = emailState,
                    passwordState = passwordState,
                    onSkip = mockedOnSkip,
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_skip))
            .performClick()

        verify { mockedOnSkip() }
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
