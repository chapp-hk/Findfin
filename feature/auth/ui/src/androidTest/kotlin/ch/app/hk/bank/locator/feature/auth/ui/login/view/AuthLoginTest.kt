package ch.app.hk.bank.locator.feature.auth.ui.login.view

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginError
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginUiState
import ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel.AuthLoginViewModel
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class AuthLoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val authLoginViewModel = mockk<AuthLoginViewModel>()

    @Test
    fun testLoading() {
        every { authLoginViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

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
            MutableStateFlow(ScreenState.Error(LoginUiState.Error(LoginError.UNKNOWN)))

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
            MutableStateFlow(ScreenState.Error(LoginUiState.Error(LoginError.INVALID_CREDENTIAL)))

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
            MutableStateFlow(ScreenState.Error(LoginUiState.Error(LoginError.ACCOUNT_DISABLED)))

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
            MutableStateFlow(ScreenState.Error(LoginUiState.Error(LoginError.TOO_MANY_REQUEST)))

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
            MutableStateFlow(ScreenState.Success(LoginUiState.Authorized))

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
