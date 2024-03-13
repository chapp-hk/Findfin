package ch.app.hk.bank.locator.feature.auth.ui.entry.view

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.core.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModel
import ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel.AuthRegisterViewModelImpl
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AuthEntryScreenRouteTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val authRegisterViewModelImpl = mockk<AuthRegisterViewModelImpl>(relaxed = true)

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val authEntryViewModel = mockk<AuthEntryViewModel>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testAuthorized() {
        val mockFinishAuth = mockk<() -> Unit>()
        every { mockFinishAuth() } just Runs

        every { authEntryViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(AuthEntryUiState.Authorized))

        composeTestRule.setContent {
            AppTheme {
                AuthEntryScreenRoute(
                    authEntryViewModel = authEntryViewModel,
                    finishAuth = mockFinishAuth,
                )
            }
        }

        verify { mockFinishAuth() }
    }

    @Test
    fun testStartAuth() {
        every { authRegisterViewModelImpl.uiState } returns
            MutableStateFlow(ScreenState.Empty)

        every { authEntryViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(AuthEntryUiState.StartAuth))

        composeTestRule.setContent {
            AppTheme {
                AuthEntryScreenRoute(
                    authEntryViewModel = authEntryViewModel,
                    finishAuth = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.auth_content_description_register))
            .assertIsDisplayed()
    }
}
