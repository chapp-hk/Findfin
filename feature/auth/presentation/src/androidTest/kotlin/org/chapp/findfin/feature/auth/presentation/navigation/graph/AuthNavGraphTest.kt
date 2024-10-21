package org.chapp.findfin.feature.auth.presentation.navigation.graph

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.feature.auth.presentation.R
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginUiState
import org.chapp.findfin.feature.auth.presentation.ui.login.viewmodel.LoginViewModel
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class AuthNavGraphTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val loginViewModel = mockk<LoginViewModel>(relaxed = true)

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val testNavController = TestNavHostController(context)

    @Before
    fun setUp() {
        hiltTestRule.inject()
        testNavController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    @Test
    fun testLoginScreenDontHaveAccount() {
        every { loginViewModel.uiState } returns MutableStateFlow(LoginUiState.None)

        composeTestRule.setContent {
            NavHost(
                navController = testNavController,
                startDestination = AuthNavGraphDestination,
            ) {
                authNavGraph(testNavController) { }
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.auth_button_dont_have_account))
            .performClick()

        testNavController.currentBackStackEntry?.destination?.route shouldBe
            AuthRegisterDestination::class.qualifiedName
    }
}
