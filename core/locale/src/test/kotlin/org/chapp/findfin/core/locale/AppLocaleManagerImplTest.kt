package org.chapp.findfin.core.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Locale

@DisplayName("AppLocaleManagerImpl unit tests")
class AppLocaleManagerImplTest {
    private val appLocaleManagerImpl = AppLocaleManagerImpl()

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
            "then getCurrentLocale() should return the first item",
    )
    fun testGetCurrentLocaleReturnsFirst() {
        mockkStatic(AppCompatDelegate::getApplicationLocales)
        every { AppCompatDelegate.getApplicationLocales() } returns
            LocaleListCompat.create(Locale.CANADA)

        appLocaleManagerImpl.getCurrentLocale() shouldBe Locale.CANADA
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

        appLocaleManagerImpl.getCurrentLocale() shouldBe Locale.ENGLISH
    }
}
