package ch.app.hk.bank.locator.feature.auth.ui.register.view

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel.AuthRegisterViewModel
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

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val authRegisterViewModel = mockk<AuthRegisterViewModel>()

    @Test
    fun testLoading() {
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
                    onAuthorized = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.auth_content_description_loading))
            .assertIsDisplayed()
    }

    @Test
    fun testError() {
        every { authRegisterViewModel.uiState } returns
            MutableStateFlow(ScreenState.Error(Throwable(), AuthRegisterUiState.Failed))

        composeTestRule.setContent {
            AppContent {
                AuthRegister(
                    authRegisterViewModel = authRegisterViewModel,
                    onAuthorized = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.auth_error_message))
            .assertIsDisplayed()
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
                    onAuthorized = mockAuthorized,
                )
            }
        }

        verify { mockAuthorized() }
    }
}
