package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.core.locale.api.LocaleResult
import org.chapp.findfin.feature.setting.presentation.R
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LanguagePreferenceViewModel unit tests")
class LanguagePreferenceViewModelTest {
    private val localeProviderManager =
        mockk<LocaleProviderManager> {
            every { getAvailableLanguages() } returns
                listOf(
                    Language(isDefault = false, displayName = "English", localeTag = "en"),
                    Language(isDefault = false, displayName = "Chinese", localeTag = "zh"),
                )
        }
    private val viewModel =
        LanguagePreferenceViewModel(
            localeProviderManager = localeProviderManager,
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

        verify { localeProviderManager.getAvailableLanguages() }
    }

    @Test
    fun `getCurrentLanguageName should return system language when locale result is Default`() {
        // Arrange
        every { localeProviderManager.getCurrentLocale() } returns LocaleResult.Default

        // Act
        val result = viewModel.getCurrentLanguageName()

        // Assert
        result shouldBe UiText.ResourceString(R.string.setting_theme_summary_system)
        verify { localeProviderManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageName should return actual language name when locale result is Custom`() {
        // Arrange
        every { localeProviderManager.getCurrentLocale() } returns
            LocaleResult.Custom(tag = "en", displayName = "English")

        // Act
        val result = viewModel.getCurrentLanguageName()

        // Assert
        result shouldBe UiText.ActualString("English")
        verify { localeProviderManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageName should return system language when locale result is Error`() {
        // Arrange
        every { localeProviderManager.getCurrentLocale() } returns
            LocaleResult.Error

        // Act
        val result = viewModel.getCurrentLanguageName()

        // Assert
        result shouldBe UiText.ResourceString(R.string.setting_theme_summary_system)
        verify { localeProviderManager.getCurrentLocale() }
    }

    @Test
    fun `getCurrentLanguageTag should invoke locale provider manager get current locale tag`() {
        // Arrange
        every { localeProviderManager.getCurrentLocaleTag() } returns ""

        // Act
        viewModel.getCurrentLanguageTag()

        // Assert
        verify { localeProviderManager.getCurrentLocaleTag() }
    }

    @Test
    fun `setLanguage should set the locale`() {
        // Arrange
        val locale = "en"
        every { localeProviderManager.setLocale(locale) } just Runs

        // Act
        viewModel.setLanguage(locale)

        // Assert
        verify { localeProviderManager.setLocale(locale) }
    }
}
