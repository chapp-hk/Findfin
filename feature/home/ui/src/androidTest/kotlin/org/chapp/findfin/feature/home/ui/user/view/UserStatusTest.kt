package org.chapp.findfin.feature.home.ui.user.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.ScreenState
import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel
import org.chapp.findfin.feature.home.ui.R
import org.chapp.findfin.feature.home.ui.user.state.UserUiState
import org.chapp.findfin.feature.home.ui.user.viewmodel.UserViewModel
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserStatusTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val userViewModel = mockk<UserViewModel>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testLoadingDisplayed() {
        every { userViewModel.uiState } returns
            MutableStateFlow(ScreenState.Loading)

        composeTestRule.setContent {
            AppContent {
                UserStatus(userViewModel = userViewModel) {}
            }
        }

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_loading))
            .assertIsDisplayed()
    }

    @Test
    fun testGuestDisplayed() {
        every { userViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(UserUiState.Guest))

        composeTestRule.setContent {
            AppContent {
                UserStatus(userViewModel = userViewModel) {}
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_login_register))
            .assertIsDisplayed()
    }

    @Test
    fun testAuthorizedDisplayed() {
        val mockUser = mockk<UserModel>(relaxed = true)

        every { userViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(UserUiState.Authorized(mockUser)))

        composeTestRule.setContent {
            AppContent {
                UserStatus(userViewModel = userViewModel) {}
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_greeting))
            .assertIsDisplayed()
    }
}
