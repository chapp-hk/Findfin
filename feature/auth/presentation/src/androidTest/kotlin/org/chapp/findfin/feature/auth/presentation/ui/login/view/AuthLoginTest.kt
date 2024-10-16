package org.chapp.findfin.feature.auth.presentation.ui.login.view

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
import org.chapp.findfin.feature.auth.presentation.R
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginError
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginUiState
import org.chapp.findfin.feature.auth.presentation.ui.login.viewmodel.AuthLoginViewModel
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

class AuthLoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val authLoginViewModel = mockk<AuthLoginViewModel>()

    @Test
    fun testLoading() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Loading)

        composeTestRule.setContent {
            AppContent {
                AuthLogin(authLoginViewModel = authLoginViewModel)
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.auth_content_description_loading))
            .assertIsDisplayed()
    }

    @Test
    fun testUnknownError() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Error(LoginError.UNKNOWN))

        composeTestRule.setContent {
            AppContent {
                AuthLogin(authLoginViewModel = authLoginViewModel)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_error_message))
            .assertIsDisplayed()
    }

    @Test
    fun testInvalidCredentialError() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Error(LoginError.INVALID_CREDENTIAL))

        composeTestRule.setContent {
            AppContent {
                AuthLogin(authLoginViewModel = authLoginViewModel)
            }
        }

        composeTestRule
            .onNodeWithText(
                text = getResourceString(R.string.auth_error_message_invalid_credential),
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(getResourceString(R.string.auth_placeholder_email))
    }

    @Test
    fun testAccountDisabledError() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Error(LoginError.ACCOUNT_DISABLED))

        composeTestRule.setContent {
            AppContent {
                AuthLogin(authLoginViewModel = authLoginViewModel)
            }
        }

        composeTestRule
            .onNodeWithText(
                text = getResourceString(R.string.auth_error_message_account_disabled),
                useUnmergedTree = true,
            )
            .assertIsDisplayed()
            .onParent()
            .assertContentDescriptionEquals(getResourceString(R.string.auth_placeholder_email))
    }

    @Test
    fun testTooManyRequestError() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Error(LoginError.TOO_MANY_REQUEST))

        composeTestRule.setContent {
            AppContent {
                AuthLogin(authLoginViewModel = authLoginViewModel)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_error_message_too_many_request))
            .assertIsDisplayed()
    }

    @Test
    fun testSuccess() {
        val mockAuthorized = mockk<() -> Unit>()
        every { mockAuthorized() } just Runs

        every { authLoginViewModel.uiState } returns
            MutableStateFlow(LoginUiState.Authorized)

        composeTestRule.setContent {
            AppContent {
                AuthLogin(
                    authLoginViewModel = authLoginViewModel,
                    onFinishAuth = mockAuthorized,
                )
            }
        }

        verify { mockAuthorized() }
    }
}
