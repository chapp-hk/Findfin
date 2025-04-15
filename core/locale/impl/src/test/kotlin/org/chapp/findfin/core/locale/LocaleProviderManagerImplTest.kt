package org.chapp.findfin.core.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.core.locale.api.LocaleResult
import org.chapp.findfin.core.locale.impl.LocaleProviderManagerImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Locale

@DisplayName("LocaleProviderManagerImpl unit tests")
class LocaleProviderManagerImplTest {
    private val appLocaleManagerImpl = LocaleProviderManagerImpl()

    @Test
    fun `test SetLocale`() {
        mockkStatic(AppCompatDelegate::setApplicationLocales)
        every { AppCompatDelegate.setApplicationLocales(any()) } just Runs

        appLocaleManagerImpl.setLocale("en")

        verify {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        }
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns non empty locale list, " +
            "then getCurrentLocale() should return Custom",
    )
    fun testGetCurrentLocaleReturnsFirst() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.create(Locale.CANADA)

        appLocaleManagerImpl.getCurrentLocale() shouldBe
            LocaleResult.Custom(
                tag = Locale.CANADA.language,
                displayName = Locale.CANADA.getDisplayLanguage(Locale.CANADA),
            )
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns empty locale list, " +
            "then getCurrentLocale() should return Default",
    )
    fun testGetCurrentLocaleReturnsDefault() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.getEmptyLocaleList()

        appLocaleManagerImpl.getCurrentLocale() shouldBe LocaleResult.Default
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns error, " +
            "then getCurrentLocale() should return Error",
    )
    fun testGetCurrentLocaleReturnsError() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } throws Error()

        appLocaleManagerImpl.getCurrentLocale() shouldBe LocaleResult.Error
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns non empty locale list, " +
            "then getCurrentLocaleTag() should return the locale tag",
    )
    fun testGetCurrentLocaleTagReturnsCustomTag() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.create(Locale.CANADA)

        appLocaleManagerImpl.getCurrentLocaleTag() shouldBe "en"
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns empty list, " +
            "then getCurrentLocaleTag() should return an empty string",
    )
    fun testGetCurrentLocaleTagReturnsDefaultTag() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.getEmptyLocaleList()

        appLocaleManagerImpl.getCurrentLocaleTag() shouldBe ""
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns Error, " +
            "then getCurrentLocaleTag() should return an empty string",
    )
    fun testGetCurrentLocaleTagReturnsErrorTag() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } throws Error()

        appLocaleManagerImpl.getCurrentLocaleTag() shouldBe ""
    }

    @Test
    fun `test getAvailableLanguages`() {
        appLocaleManagerImpl.getAvailableLanguages() shouldBe
            listOf(
                Language(
                    isDefault = true,
                    localeTag = "",
                    displayName = "",
                ),
                Language(
                    isDefault = false,
                    localeTag = "en",
                    displayName = "English",
                ),
                Language(
                    isDefault = false,
                    localeTag = "zh",
                    displayName = "中文",
                ),
            )
    }
}
