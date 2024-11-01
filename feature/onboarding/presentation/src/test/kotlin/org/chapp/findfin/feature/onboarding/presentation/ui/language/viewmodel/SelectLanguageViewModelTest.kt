package org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.design.ui.foundation.ScreenState
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.AppLocaleManager
import org.chapp.findfin.feature.onboarding.domain.fetch.usecase.FetchAllBankLocationsWithLanguageUseCase
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("SelectLanguageViewModel unit tests")
class SelectLanguageViewModelTest {
    private val languageRepository = mockk<LanguageRepository>()
    private val appLocaleManager = mockk<AppLocaleManager>()
    private val fetchAllLocatorsWithLanguage =
        mockk<FetchAllBankLocationsWithLanguageUseCase>(relaxed = true)

    @BeforeEach
    fun setUp() {
        every { languageRepository.getAvailableLanguages() } returns
            listOf(
                Language(
                    isDefault = true,
                    name = "",
                    tag = "",
                ),
                Language(
                    isDefault = false,
                    name = "English",
                    tag = "en",
                ),
                Language(
                    isDefault = false,
                    name = "Chinese",
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
                    displayName = UiText.ResourceString(resId = R.string.onboarding_select_language_default),
                    tag = "",
                ),
                SelectLanguageUiModel(
                    displayName = UiText.ActualString(value = "English"),
                    tag = "en",
                ),
                SelectLanguageUiModel(
                    displayName = UiText.ActualString(value = "Chinese"),
                    tag = "zh",
                ),
            )
    }

    @Test
    @DisplayName(
        "When setLanguage, should invoke appLocaleRepository.setLocale() with input language value",
    )
    fun `test setLanguage should invoke appLocaleRepository setLocale`() {
        every { appLocaleManager.setLocale(any()) } just Runs

        createViewModel().setLanguage("en")

        verify { appLocaleManager.setLocale("en") }
    }

    @Test
    @DisplayName(
        "test uiState, when fetchAllLocatorsWithLanguage() returns true, " +
            "then SelectLanguageViewModelImpl.uiState should be ScreenState.Success",
    )
    fun testUiStateSuccess() =
        runTest {
            every { appLocaleManager.setLocale(any()) } just Runs
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
            every { appLocaleManager.setLocale(any()) } just Runs
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
        SelectLanguageViewModel(
            languageRepository = languageRepository,
            appLocaleManager = appLocaleManager,
            fetchAllLocatorsWithLanguage = fetchAllLocatorsWithLanguage,
        )
}
