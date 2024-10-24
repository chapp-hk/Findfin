package org.chapp.findfin.core.locale.impl

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import org.chapp.findfin.core.locale.api.AppLocale
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Locale

@DisplayName("AppLocaleRepositoryImpl unit tests")
class AppLocaleRepositoryImplTest {
    private val appLocaleRepositoryImpl = AppLocaleRepositoryImpl()

    @Test
    fun `test SetLocale`() {
        mockkStatic(AppCompatDelegate::setApplicationLocales)
        every { AppCompatDelegate.setApplicationLocales(any()) } just Runs

        appLocaleRepositoryImpl.setLocale("en")

        verify {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        }
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns non empty locale list, " +
            "then getCurrentLocale() should return the first item",
    )
    fun testGetCurrentLocaleReturnsFirst() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.create(Locale.CANADA)

        appLocaleRepositoryImpl.getCurrentLocale() shouldBe Locale.CANADA
    }

    @Test
    @DisplayName(
        "When AppCompatDelegate.getApplicationLocales() returns empty locale list, " +
            "then getCurrentLocale() should return default item",
    )
    fun testGetCurrentLocaleReturnsDefault() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.create()

        appLocaleRepositoryImpl.getCurrentLocale() shouldBe Locale.ENGLISH
    }

    @Test
    fun test_availableLocales() {
        appLocaleRepositoryImpl.availableLocales() shouldBe
            listOf(
                AppLocale(
                    displayName = "English",
                    tag = "en",
                ),
                AppLocale(
                    displayName = "中文",
                    tag = "zh",
                ),
            )
    }
}
