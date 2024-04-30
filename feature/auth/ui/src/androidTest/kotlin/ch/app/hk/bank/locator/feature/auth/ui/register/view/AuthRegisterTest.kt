package ch.app.hk.bank.locator.feature.auth.ui.register.view

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterError
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel.AuthRegisterViewModel
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class AuthRegisterTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val authRegisterViewModel = mockk<AuthRegisterViewModel>()

    @Test
    fun testLoading() {
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

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
            MutableStateFlow(ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN)))

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
            MutableStateFlow(ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL)))

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
            MutableStateFlow(ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD)))

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
            MutableStateFlow(ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE)))

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
            MutableStateFlow(ScreenState.Success(AuthRegisterUiState.Authorized))

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
