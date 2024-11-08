package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.AppLocaleManager
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
import org.chapp.findfin.feature.setting.presentation.R
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Locale

@DisplayName("LanguagePreferenceViewModel unit tests")
class LanguagePreferenceViewModelTest {
    private val languageRepository =
        mockk<LanguageRepository> {
            every { getAvailableLanguages() } returns
                listOf(
                    Language(isDefault = false, name = "English", tag = "en"),
                    Language(isDefault = false, name = "Chinese", tag = "zh"),
                )
        }
    private val appLocaleManager = mockk<AppLocaleManager>()
    private val viewModel =
        LanguagePreferenceViewModel(
            languageRepository = languageRepository,
            appLocaleManager = appLocaleManager,
        )

    @Test
    fun `availableLanguages should return mapped languages`() {
        // Act
        val result = viewModel.availableLanguages

        // Assert
        result shouldBe
            listOf(
                LanguagePreferenceItem(
                    title = UiText.ActualString("English"),
                    value = "en",
                ),
                LanguagePreferenceItem(
                    title = UiText.ActualString("Chinese"),
                    value = "zh",
                ),
            )

        verify { languageRepository.getAvailableLanguages() }
    }

    @Test
    fun `getCurrentLanguageName should return system language when locale is null`() {
        // Arrange
        every { appLocaleManager.getCurrentLocale() } returns null

        // Act
        val result = viewModel.getCurrentLanguageName()

        // Assert
        result shouldBe UiText.ResourceString(R.string.setting_theme_summary_system)
        verify { appLocaleManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageName should return actual language name when locale is not null`() {
        // Arrange
        val locale = Locale("en")
        every { appLocaleManager.getCurrentLocale() } returns locale

        // Act
        val result = viewModel.getCurrentLanguageName()

        // Assert
        result shouldBe UiText.ActualString("English")
        verify { appLocaleManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageTag should return empty string when locale is null`() {
        // Arrange
        every { appLocaleManager.getCurrentLocale() } returns null

        // Act
        val result = viewModel.getCurrentLanguageTag()

        // Assert
        result shouldBe ""
        verify { appLocaleManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageTag should return language tag when locale is not null`() {
        // Arrange
        val locale = Locale("zh")
        every { appLocaleManager.getCurrentLocale() } returns locale

        // Act
        val result = viewModel.getCurrentLanguageTag()

        // Assert
        result shouldBe "zh"
        verify { appLocaleManager.getCurrentLocale() }
    }

    @Test
    fun `setLanguage should set the locale`() {
        // Arrange
        val locale = "en"
        every { appLocaleManager.setLocale(locale) } just Runs

        // Act
        viewModel.setLanguage(locale)

        // Assert
        verify { appLocaleManager.setLocale(locale) }
    }
}
