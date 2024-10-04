package org.chapp.findfin.feature.onboarding.navigation.graph

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.feature.onboarding.navigation.viewmodel.OnboardingNavState
import org.chapp.findfin.feature.onboarding.navigation.viewmodel.OnboardingNavViewModel
import org.chapp.findfin.testing.instrument.ActivityResultTestRule
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class OnboardingNavGraphTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 2)
    val activityResultTestRule = ActivityResultTestRule(context = context)

    @BindValue
    internal val onboardingNavViewModel = mockk<OnboardingNavViewModel>(relaxUnitFun = true)

    private val navController = TestNavHostController(context)

    @Before
    fun setupAppNavHost() {
        hiltTestRule.inject()
        navController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    @Test
    fun testFinishOnboardingInvokedWhenStart() {
        val finishOnboarding = mockk<() -> Unit>(relaxed = true)

        every { onboardingNavViewModel.navState } returns MutableStateFlow(OnboardingNavState.IsFinishedOnboard)

        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = OnboardingNavGraphDestination,
            ) {
                onboardingNavGraph(
                    navController = navController,
                    finishOnboarding = finishOnboarding,
                )
            }
        }

        verify {
            finishOnboarding()
        }
    }

    @Test
    fun testFinishOnboardingNotInvokedWhenStart() {
        val finishOnboarding = mockk<() -> Unit>(relaxed = true)

        every { onboardingNavViewModel.navState } returns MutableStateFlow(OnboardingNavState.NotFinishedOnboard)

        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = OnboardingNavGraphDestination,
            ) {
                onboardingNavGraph(
                    navController = navController,
                    finishOnboarding = finishOnboarding,
                )
            }
        }

        verify {
            finishOnboarding wasNot Called
        }

        navController.currentDestination?.route shouldBe
            OnboardingSelectLanguageDestination::class.qualifiedName
    }
}
