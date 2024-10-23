package org.chapp.findfin.feature.auth.presentation.ui.register.view

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.feature.auth.presentation.R
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterError
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterUiState
import org.chapp.findfin.feature.auth.presentation.ui.register.viewmodel.RegisterViewModel
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class RegisterTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val registerViewModel = mockk<RegisterViewModel>()

    @Test
    fun testLoading() {
        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Loading)

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = {},
                    onHaveAccount = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_content_description_loading))
            .assertIsDisplayed()
    }

    @Test
    fun testUnknownError() {
        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Error(RegisterError.UNKNOWN))

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = {},
                    onHaveAccount = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_error_message))
            .assertIsDisplayed()
    }

    @Test
    fun testInvalidEmailError() {
        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Error(RegisterError.INVALID_EMAIL))

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = {},
                    onHaveAccount = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = getResourceString(R.string.auth_error_message_invalid_email),
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(getResourceString(R.string.auth_placeholder_email))
    }

    @Test
    fun testWeakPasswordError() {
        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Error(RegisterError.WEAK_PASSWORD))

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = {},
                    onHaveAccount = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = getResourceString(R.string.auth_error_message_password_strength),
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(getResourceString(R.string.auth_placeholder_password))
    }

    @Test
    fun testEmailAlreadyInUseError() {
        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Error(RegisterError.EMAIL_ALREADY_IN_USE))

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = {},
                    onHaveAccount = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                text = getResourceString(R.string.auth_error_message_email_already_in_use),
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(getResourceString(R.string.auth_placeholder_email))
    }

    @Test
    fun testSuccess() {
        val mockAuthorized = mockk<() -> Unit>()
        every { mockAuthorized() } just Runs

        every { registerViewModel.uiState } returns
            MutableStateFlow(RegisterUiState.Authorized)

        composeTestRule.setContent {
            AppContent {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onFinishAuth = mockAuthorized,
                    onHaveAccount = {},
                )
            }
        }

        verify { mockAuthorized() }
    }
}
