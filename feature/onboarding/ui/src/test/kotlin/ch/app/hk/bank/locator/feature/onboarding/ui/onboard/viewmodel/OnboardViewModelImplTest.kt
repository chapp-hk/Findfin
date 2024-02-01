package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("OnboardViewModelImpl unit tests")
class OnboardViewModelImplTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>()

    @ParameterizedTest(
        name = "test uiState, when appPreferencesRepository.getBoolean() returns {0}, " +
            "then OnboardViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(UiStateArgumentProvider::class)
    fun testUiState(
        mockedReturnValue: Boolean,
        expectedResult: OnboardUiState,
    ) = runTest {
        every { appPreferencesRepository.getBoolean(any()) } returns flowOf(mockedReturnValue)

        val onboardViewModel = createOnboardViewModel()

        onboardViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Loading
            awaitItem() shouldBe ScreenState.Success(expectedResult)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class UiStateArgumentProvider : ArgumentsProvider {
        override fun provideArguments(p0: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                arguments(false, OnboardUiState.SelectLanguage),
                arguments(true, OnboardUiState.NavigateToHome),
            )
        }
    }

    private fun createOnboardViewModel() =
        OnboardViewModelImpl(appPreferencesRepository = appPreferencesRepository)
}
