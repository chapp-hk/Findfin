package org.chapp.findfin.feature.auth.ui.register.view

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
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.feature.auth.ui.R
import org.chapp.findfin.feature.auth.ui.register.state.AuthRegisterError
import org.chapp.findfin.feature.auth.ui.register.state.AuthRegisterUiState
import org.chapp.findfin.feature.auth.ui.register.viewmodel.AuthRegisterViewModel
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class AuthRegisterTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val authRegisterViewModel = mockk<AuthRegisterViewModel>()

    @Test
    fun testLoading() {
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Loading)

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
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
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN))

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
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
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL))

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
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
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD))

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
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
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE))

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
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

        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(AuthRegisterUiState.Authorized)

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
                    onFinishAuth = mockAuthorized,
                    onHaveAccount = {},
                )
            }
        }

        verify { mockAuthorized() }
    }
}
