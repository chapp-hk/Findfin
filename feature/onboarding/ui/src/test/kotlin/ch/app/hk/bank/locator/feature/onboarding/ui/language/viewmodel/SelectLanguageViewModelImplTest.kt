package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.locale.api.AppLocale
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase.FetchAllLocatorsWithLanguageUseCase
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("SelectLanguageViewModelImpl unit tests")
class SelectLanguageViewModelImplTest {
    private val appLocaleRepository = mockk<AppLocaleRepository>()
    private val fetchAllLocatorsWithLanguage = mockk<FetchAllLocatorsWithLanguageUseCase>(relaxed = true)

    @BeforeEach
    fun setUp() {
        every { appLocaleRepository.availableLocales() } returns
            listOf(
                AppLocale(
                    displayName = "English",
                    tag = "en",
                ),
                AppLocale(
                    displayName = "Chinese",
                    tag = "zh",
                ),
            )
    }

    @Test
    @DisplayName(
        "When get availableLanguages, should return list of SelectLanguageUiModel",
    )
    fun testAvailableLanguages() {
        createViewModel().availableLanguages shouldBe
            listOf(
                SelectLanguageUiModel(
                    displayName = "English",
                    tag = "en",
                ),
                SelectLanguageUiModel(
                    displayName = "Chinese",
                    tag = "zh",
                ),
            )
    }

    @Test
    @DisplayName(
        "When setLanguage, should invoke appLocaleRepository.setLocale() with input language value",
    )
    fun `test setLanguage should invoke appLocaleRepository setLocale`() {
        every { appLocaleRepository.setLocale(any()) } just Runs

        createViewModel().setLanguage("en")

        verify { appLocaleRepository.setLocale("en") }
    }

    @Test
    @DisplayName(
        "test uiState, when fetchAllLocatorsWithLanguage() returns true, " +
            "then SelectLanguageViewModelImpl.uiState should be ScreenState.Success",
    )
    fun testUiStateSuccess() =
        runTest {
            every { appLocaleRepository.setLocale(any()) } just Runs
            coEvery { fetchAllLocatorsWithLanguage() } returns true

            val viewModel = createViewModel()
            viewModel.setLanguage("en")

            viewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Empty
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Success(SelectLanguageUiState("en"))
            }
        }

    @Test
    @DisplayName(
        "test uiState, when fetchAllLocatorsWithLanguage() returns false, " +
            "then SelectLanguageViewModelImpl.uiState should be ScreenState.Error",
    )
    fun testUiStateError() =
        runTest {
            every { appLocaleRepository.setLocale(any()) } just Runs
            coEvery { fetchAllLocatorsWithLanguage() } returns false

            val viewModel = createViewModel()
            viewModel.setLanguage("en")

            viewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Empty
                awaitItem() shouldBe ScreenState.Loading
                awaitItem().shouldBeInstanceOf<ScreenState.Error<SelectLanguageUiState, String>>()
                    .error shouldBe "en"
            }
        }

    private fun createViewModel() =
        SelectLanguageViewModelImpl(
            appLocaleRepository = appLocaleRepository,
            fetchAllLocatorsWithLanguage = fetchAllLocatorsWithLanguage,
        )
}
