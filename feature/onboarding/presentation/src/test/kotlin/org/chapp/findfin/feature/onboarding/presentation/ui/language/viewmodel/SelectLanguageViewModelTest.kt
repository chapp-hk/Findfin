package org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.chapp.findfin.feature.setting.domain.fetch.usecase.FetchAllBankLocationsWithLanguageUseCase
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("SelectLanguageViewModel unit tests")
class SelectLanguageViewModelTest {
    private val localeProviderManager = mockk<LocaleProviderManager>()
    private val fetchAllLocatorsWithLanguage =
        mockk<FetchAllBankLocationsWithLanguageUseCase>(relaxed = true)

    @BeforeEach
    fun setUp() {
        every { localeProviderManager.getAvailableLanguages() } returns
            listOf(
                Language(
                    isDefault = true,
                    displayName = "",
                    localeTag = "",
                ),
                Language(
                    isDefault = false,
                    displayName = "English",
                    localeTag = "en",
                ),
                Language(
                    isDefault = false,
                    displayName = "Chinese",
                    localeTag = "zh",
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
        every { localeProviderManager.setLocale(any()) } just Runs

        createViewModel().setLanguage("en")

        verify { localeProviderManager.setLocale("en") }
    }

    @Test
    @DisplayName(
        "test uiState, when fetchAllLocatorsWithLanguage() returns true, " +
            "then SelectLanguageViewModelImpl.uiState should be ScreenState.Success",
    )
    fun testUiStateSuccess() =
        runTest {
            every { localeProviderManager.setLocale(any()) } just Runs
            coEvery { fetchAllLocatorsWithLanguage() } returns true

            val viewModel = createViewModel()
            viewModel.setLanguage("en")

            viewModel.uiState.test {
                awaitItem() shouldBe SelectLanguageUiState.Initial
                awaitItem() shouldBe SelectLanguageUiState.Loading
                awaitItem() shouldBe SelectLanguageUiState.Success(selectedLanguageTag = "en")
            }

            coVerify { fetchAllLocatorsWithLanguage() }
        }

    @Test
    @DisplayName(
        "test uiState, when fetchAllLocatorsWithLanguage() returns false, " +
            "then SelectLanguageViewModelImpl.uiState should be ScreenState.Error",
    )
    fun testUiStateError() =
        runTest {
            every { localeProviderManager.setLocale(any()) } just Runs
            coEvery { fetchAllLocatorsWithLanguage() } returns false

            val viewModel = createViewModel()
            viewModel.setLanguage("en")

            viewModel.uiState.test {
                awaitItem() shouldBe SelectLanguageUiState.Initial
                awaitItem() shouldBe SelectLanguageUiState.Loading
                awaitItem() shouldBe SelectLanguageUiState.Error(selectedLanguageTag = "en")
            }

            coVerify { fetchAllLocatorsWithLanguage() }
        }

    private fun createViewModel() =
        SelectLanguageViewModel(
            localeProviderManager = localeProviderManager,
            fetchAllLocatorsWithLanguage = fetchAllLocatorsWithLanguage,
        )
}
