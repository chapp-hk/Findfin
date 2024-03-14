package ch.app.hk.bank.locator.core.design.theme

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Theme unit tests")
class ThemeTest {
    @Test
    fun `test LightColors`() {
        LightColors.apply {
            primary shouldBe md_theme_light_primary
            onPrimary shouldBe md_theme_light_onPrimary
            primaryContainer shouldBe md_theme_light_primaryContainer
            onPrimaryContainer shouldBe md_theme_light_onPrimaryContainer
            secondary shouldBe md_theme_light_secondary
            onSecondary shouldBe md_theme_light_onSecondary
            secondaryContainer shouldBe md_theme_light_secondaryContainer
            onSecondaryContainer shouldBe md_theme_light_onSecondaryContainer
            tertiary shouldBe md_theme_light_tertiary
            onTertiary shouldBe md_theme_light_onTertiary
            tertiaryContainer shouldBe md_theme_light_tertiaryContainer
            onTertiaryContainer shouldBe md_theme_light_onTertiaryContainer
            error shouldBe md_theme_light_error
            errorContainer shouldBe md_theme_light_errorContainer
            onError shouldBe md_theme_light_onError
            onErrorContainer shouldBe md_theme_light_onErrorContainer
            background shouldBe md_theme_light_background
            onBackground shouldBe md_theme_light_onBackground
            surface shouldBe md_theme_light_surface
            onSurface shouldBe md_theme_light_onSurface
            surfaceVariant shouldBe md_theme_light_surfaceVariant
            onSurfaceVariant shouldBe md_theme_light_onSurfaceVariant
            outline shouldBe md_theme_light_outline
            inverseOnSurface shouldBe md_theme_light_inverseOnSurface
            inverseSurface shouldBe md_theme_light_inverseSurface
            inversePrimary shouldBe md_theme_light_inversePrimary
            surfaceTint shouldBe md_theme_light_surfaceTint
            outlineVariant shouldBe md_theme_light_outlineVariant
            scrim shouldBe md_theme_light_scrim
        }
    }

    @Test
    fun `test DarkColors`() {
        DarkColors.apply {
            primary shouldBe md_theme_dark_primary
            onPrimary shouldBe md_theme_dark_onPrimary
            primaryContainer shouldBe md_theme_dark_primaryContainer
            onPrimaryContainer shouldBe md_theme_dark_onPrimaryContainer
            secondary shouldBe md_theme_dark_secondary
            onSecondary shouldBe md_theme_dark_onSecondary
            secondaryContainer shouldBe md_theme_dark_secondaryContainer
            onSecondaryContainer shouldBe md_theme_dark_onSecondaryContainer
            tertiary shouldBe md_theme_dark_tertiary
            onTertiary shouldBe md_theme_dark_onTertiary
            tertiaryContainer shouldBe md_theme_dark_tertiaryContainer
            onTertiaryContainer shouldBe md_theme_dark_onTertiaryContainer
            error shouldBe md_theme_dark_error
            errorContainer shouldBe md_theme_dark_errorContainer
            onError shouldBe md_theme_dark_onError
            onErrorContainer shouldBe md_theme_dark_onErrorContainer
            background shouldBe md_theme_dark_background
            onBackground shouldBe md_theme_dark_onBackground
            surface shouldBe md_theme_dark_surface
            onSurface shouldBe md_theme_dark_onSurface
            surfaceVariant shouldBe md_theme_dark_surfaceVariant
            onSurfaceVariant shouldBe md_theme_dark_onSurfaceVariant
            outline shouldBe md_theme_dark_outline
            inverseOnSurface shouldBe md_theme_dark_inverseOnSurface
            inverseSurface shouldBe md_theme_dark_inverseSurface
            inversePrimary shouldBe md_theme_dark_inversePrimary
            surfaceTint shouldBe md_theme_dark_surfaceTint
            outlineVariant shouldBe md_theme_dark_outlineVariant
            scrim shouldBe md_theme_dark_scrim
        }
    }
}
