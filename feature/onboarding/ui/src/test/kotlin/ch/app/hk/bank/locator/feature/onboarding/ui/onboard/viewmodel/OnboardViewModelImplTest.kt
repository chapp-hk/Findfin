package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(MainDispatcherExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("OnboardViewModelImpl unit tests")
class OnboardViewModelImplTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>()

    @ParameterizedTest(
        name = "test uiState, when appPreferencesRepository.getBoolean() returns {0}, " +
            "then OnboardViewModelImpl.uiState should be {1}"
    )
    @MethodSource("uiStateArguments")
    fun testUiState(
        mockedReturnValue: Boolean?,
        expectedResult: OnboardUiState,
    ) = runTest {
        every { appPreferencesRepository.getBoolean(any()) } returns flowOf(mockedReturnValue)

        val onboardViewModel = createOnboardViewModel()

        onboardViewModel.uiState.test {
            awaitItem() shouldBe OnboardUiState.None
            awaitItem() shouldBe expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun uiStateArguments() =
        listOf(
            arguments(null, OnboardUiState.SelectLanguage),
            arguments(false, OnboardUiState.SelectLanguage),
            arguments(true, OnboardUiState.NavigateToHome),
        )

    private fun createOnboardViewModel() =
        OnboardViewModelImpl(appPreferencesRepository = appPreferencesRepository)
}
