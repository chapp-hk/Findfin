package ch.app.hk.bank.locator.core.design.theme

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Theme unit tests")
class ThemeTest {
    @Test
    fun `test lightScheme`() {
        lightScheme.apply {
            primary shouldBe primaryLight
            onPrimary shouldBe onPrimaryLight
            primaryContainer shouldBe primaryContainerLight
            onPrimaryContainer shouldBe onPrimaryContainerLight
            secondary shouldBe secondaryLight
            onSecondary shouldBe onSecondaryLight
            secondaryContainer shouldBe secondaryContainerLight
            onSecondaryContainer shouldBe onSecondaryContainerLight
            tertiary shouldBe tertiaryLight
            onTertiary shouldBe onTertiaryLight
            tertiaryContainer shouldBe tertiaryContainerLight
            onTertiaryContainer shouldBe onTertiaryContainerLight
            error shouldBe errorLight
            onError shouldBe onErrorLight
            errorContainer shouldBe errorContainerLight
            onErrorContainer shouldBe onErrorContainerLight
            background shouldBe backgroundLight
            onBackground shouldBe onBackgroundLight
            surface shouldBe surfaceLight
            onSurface shouldBe onSurfaceLight
            surfaceVariant shouldBe surfaceVariantLight
            onSurfaceVariant shouldBe onSurfaceVariantLight
            outline shouldBe outlineLight
            outlineVariant shouldBe outlineVariantLight
            scrim shouldBe scrimLight
            inverseSurface shouldBe inverseSurfaceLight
            inverseOnSurface shouldBe inverseOnSurfaceLight
            inversePrimary shouldBe inversePrimaryLight
            surfaceDim shouldBe surfaceDimLight
            surfaceBright shouldBe surfaceBrightLight
            surfaceContainerLowest shouldBe surfaceContainerLowestLight
            surfaceContainerLow shouldBe surfaceContainerLowLight
            surfaceContainer shouldBe surfaceContainerLight
            surfaceContainerHigh shouldBe surfaceContainerHighLight
            surfaceContainerHighest shouldBe surfaceContainerHighestLight
        }
    }

    @Test
    fun `test darkScheme`() {
        darkScheme.apply {
            primary shouldBe primaryDark
            onPrimary shouldBe onPrimaryDark
            primaryContainer shouldBe primaryContainerDark
            onPrimaryContainer shouldBe onPrimaryContainerDark
            secondary shouldBe secondaryDark
            onSecondary shouldBe onSecondaryDark
            secondaryContainer shouldBe secondaryContainerDark
            onSecondaryContainer shouldBe onSecondaryContainerDark
            tertiary shouldBe tertiaryDark
            onTertiary shouldBe onTertiaryDark
            tertiaryContainer shouldBe tertiaryContainerDark
            onTertiaryContainer shouldBe onTertiaryContainerDark
            error shouldBe errorDark
            onError shouldBe onErrorDark
            errorContainer shouldBe errorContainerDark
            onErrorContainer shouldBe onErrorContainerDark
            background shouldBe backgroundDark
            onBackground shouldBe onBackgroundDark
            surface shouldBe surfaceDark
            onSurface shouldBe onSurfaceDark
            surfaceVariant shouldBe surfaceVariantDark
            onSurfaceVariant shouldBe onSurfaceVariantDark
            outline shouldBe outlineDark
            outlineVariant shouldBe outlineVariantDark
            scrim shouldBe scrimDark
            inverseSurface shouldBe inverseSurfaceDark
            inverseOnSurface shouldBe inverseOnSurfaceDark
            inversePrimary shouldBe inversePrimaryDark
            surfaceDim shouldBe surfaceDimDark
            surfaceBright shouldBe surfaceBrightDark
            surfaceContainerLowest shouldBe surfaceContainerLowestDark
            surfaceContainerLow shouldBe surfaceContainerLowDark
            surfaceContainer shouldBe surfaceContainerDark
            surfaceContainerHigh shouldBe surfaceContainerHighDark
            surfaceContainerHighest shouldBe surfaceContainerHighestDark
        }
    }

    @Test
    fun `test mediumContrastLightColorScheme`() {
        mediumContrastLightColorScheme.apply {
            primary shouldBe primaryLightMediumContrast
            onPrimary shouldBe onPrimaryLightMediumContrast
            primaryContainer shouldBe primaryContainerLightMediumContrast
            onPrimaryContainer shouldBe onPrimaryContainerLightMediumContrast
            secondary shouldBe secondaryLightMediumContrast
            onSecondary shouldBe onSecondaryLightMediumContrast
            secondaryContainer shouldBe secondaryContainerLightMediumContrast
            onSecondaryContainer shouldBe onSecondaryContainerLightMediumContrast
            tertiary shouldBe tertiaryLightMediumContrast
            onTertiary shouldBe onTertiaryLightMediumContrast
            tertiaryContainer shouldBe tertiaryContainerLightMediumContrast
            onTertiaryContainer shouldBe onTertiaryContainerLightMediumContrast
            error shouldBe errorLightMediumContrast
            onError shouldBe onErrorLightMediumContrast
            errorContainer shouldBe errorContainerLightMediumContrast
            onErrorContainer shouldBe onErrorContainerLightMediumContrast
            background shouldBe backgroundLightMediumContrast
            onBackground shouldBe onBackgroundLightMediumContrast
            surface shouldBe surfaceLightMediumContrast
            onSurface shouldBe onSurfaceLightMediumContrast
            surfaceVariant shouldBe surfaceVariantLightMediumContrast
            onSurfaceVariant shouldBe onSurfaceVariantLightMediumContrast
            outline shouldBe outlineLightMediumContrast
            outlineVariant shouldBe outlineVariantLightMediumContrast
            scrim shouldBe scrimLightMediumContrast
            inverseSurface shouldBe inverseSurfaceLightMediumContrast
            inverseOnSurface shouldBe inverseOnSurfaceLightMediumContrast
            inversePrimary shouldBe inversePrimaryLightMediumContrast
            surfaceDim shouldBe surfaceDimLightMediumContrast
            surfaceBright shouldBe surfaceBrightLightMediumContrast
            surfaceContainerLowest shouldBe surfaceContainerLowestLightMediumContrast
            surfaceContainerLow shouldBe surfaceContainerLowLightMediumContrast
            surfaceContainer shouldBe surfaceContainerLightMediumContrast
            surfaceContainerHigh shouldBe surfaceContainerHighLightMediumContrast
            surfaceContainerHighest shouldBe surfaceContainerHighestLightMediumContrast
        }
    }

    @Test
    fun `test highContrastLightColorScheme`() {
        highContrastLightColorScheme.apply {
            primary shouldBe primaryLightHighContrast
            onPrimary shouldBe onPrimaryLightHighContrast
            primaryContainer shouldBe primaryContainerLightHighContrast
            onPrimaryContainer shouldBe onPrimaryContainerLightHighContrast
            secondary shouldBe secondaryLightHighContrast
            onSecondary shouldBe onSecondaryLightHighContrast
            secondaryContainer shouldBe secondaryContainerLightHighContrast
            onSecondaryContainer shouldBe onSecondaryContainerLightHighContrast
            tertiary shouldBe tertiaryLightHighContrast
            onTertiary shouldBe onTertiaryLightHighContrast
            tertiaryContainer shouldBe tertiaryContainerLightHighContrast
            onTertiaryContainer shouldBe onTertiaryContainerLightHighContrast
            error shouldBe errorLightHighContrast
            onError shouldBe onErrorLightHighContrast
            errorContainer shouldBe errorContainerLightHighContrast
            onErrorContainer shouldBe onErrorContainerLightHighContrast
            background shouldBe backgroundLightHighContrast
            onBackground shouldBe onBackgroundLightHighContrast
            surface shouldBe surfaceLightHighContrast
            onSurface shouldBe onSurfaceLightHighContrast
            surfaceVariant shouldBe surfaceVariantLightHighContrast
            onSurfaceVariant shouldBe onSurfaceVariantLightHighContrast
            outline shouldBe outlineLightHighContrast
            outlineVariant shouldBe outlineVariantLightHighContrast
            scrim shouldBe scrimLightHighContrast
            inverseSurface shouldBe inverseSurfaceLightHighContrast
            inverseOnSurface shouldBe inverseOnSurfaceLightHighContrast
            inversePrimary shouldBe inversePrimaryLightHighContrast
            surfaceDim shouldBe surfaceDimLightHighContrast
            surfaceBright shouldBe surfaceBrightLightHighContrast
            surfaceContainerLowest shouldBe surfaceContainerLowestLightHighContrast
            surfaceContainerLow shouldBe surfaceContainerLowLightHighContrast
            surfaceContainer shouldBe surfaceContainerLightHighContrast
            surfaceContainerHigh shouldBe surfaceContainerHighLightHighContrast
            surfaceContainerHighest shouldBe surfaceContainerHighestLightHighContrast
        }
    }

    @Test
    fun `test mediumContrastDarkColorScheme`() {
        mediumContrastDarkColorScheme.apply {
            primary shouldBe primaryDarkMediumContrast
            onPrimary shouldBe onPrimaryDarkMediumContrast
            primaryContainer shouldBe primaryContainerDarkMediumContrast
            onPrimaryContainer shouldBe onPrimaryContainerDarkMediumContrast
            secondary shouldBe secondaryDarkMediumContrast
            onSecondary shouldBe onSecondaryDarkMediumContrast
            secondaryContainer shouldBe secondaryContainerDarkMediumContrast
            onSecondaryContainer shouldBe onSecondaryContainerDarkMediumContrast
            tertiary shouldBe tertiaryDarkMediumContrast
            onTertiary shouldBe onTertiaryDarkMediumContrast
            tertiaryContainer shouldBe tertiaryContainerDarkMediumContrast
            onTertiaryContainer shouldBe onTertiaryContainerDarkMediumContrast
            error shouldBe errorDarkMediumContrast
            onError shouldBe onErrorDarkMediumContrast
            errorContainer shouldBe errorContainerDarkMediumContrast
            onErrorContainer shouldBe onErrorContainerDarkMediumContrast
            background shouldBe backgroundDarkMediumContrast
            onBackground shouldBe onBackgroundDarkMediumContrast
            surface shouldBe surfaceDarkMediumContrast
            onSurface shouldBe onSurfaceDarkMediumContrast
            surfaceVariant shouldBe surfaceVariantDarkMediumContrast
            onSurfaceVariant shouldBe onSurfaceVariantDarkMediumContrast
            outline shouldBe outlineDarkMediumContrast
            outlineVariant shouldBe outlineVariantDarkMediumContrast
            scrim shouldBe scrimDarkMediumContrast
            inverseSurface shouldBe inverseSurfaceDarkMediumContrast
            inverseOnSurface shouldBe inverseOnSurfaceDarkMediumContrast
            inversePrimary shouldBe inversePrimaryDarkMediumContrast
            surfaceDim shouldBe surfaceDimDarkMediumContrast
            surfaceBright shouldBe surfaceBrightDarkMediumContrast
            surfaceContainerLowest shouldBe surfaceContainerLowestDarkMediumContrast
            surfaceContainerLow shouldBe surfaceContainerLowDarkMediumContrast
            surfaceContainer shouldBe surfaceContainerDarkMediumContrast
            surfaceContainerHigh shouldBe surfaceContainerHighDarkMediumContrast
            surfaceContainerHighest shouldBe surfaceContainerHighestDarkMediumContrast
        }
    }

    @Test
    fun `test highContrastDarkColorScheme`() {
        highContrastDarkColorScheme.apply {
            primary shouldBe primaryDarkHighContrast
            onPrimary shouldBe onPrimaryDarkHighContrast
            primaryContainer shouldBe primaryContainerDarkHighContrast
            onPrimaryContainer shouldBe onPrimaryContainerDarkHighContrast
            secondary shouldBe secondaryDarkHighContrast
            onSecondary shouldBe onSecondaryDarkHighContrast
            secondaryContainer shouldBe secondaryContainerDarkHighContrast
            onSecondaryContainer shouldBe onSecondaryContainerDarkHighContrast
            tertiary shouldBe tertiaryDarkHighContrast
            onTertiary shouldBe onTertiaryDarkHighContrast
            tertiaryContainer shouldBe tertiaryContainerDarkHighContrast
            onTertiaryContainer shouldBe onTertiaryContainerDarkHighContrast
            error shouldBe errorDarkHighContrast
            onError shouldBe onErrorDarkHighContrast
            errorContainer shouldBe errorContainerDarkHighContrast
            onErrorContainer shouldBe onErrorContainerDarkHighContrast
            background shouldBe backgroundDarkHighContrast
            onBackground shouldBe onBackgroundDarkHighContrast
            surface shouldBe surfaceDarkHighContrast
            onSurface shouldBe onSurfaceDarkHighContrast
            surfaceVariant shouldBe surfaceVariantDarkHighContrast
            onSurfaceVariant shouldBe onSurfaceVariantDarkHighContrast
            outline shouldBe outlineDarkHighContrast
            outlineVariant shouldBe outlineVariantDarkHighContrast
            scrim shouldBe scrimDarkHighContrast
            inverseSurface shouldBe inverseSurfaceDarkHighContrast
            inverseOnSurface shouldBe inverseOnSurfaceDarkHighContrast
            inversePrimary shouldBe inversePrimaryDarkHighContrast
            surfaceDim shouldBe surfaceDimDarkHighContrast
            surfaceBright shouldBe surfaceBrightDarkHighContrast
            surfaceContainerLowest shouldBe surfaceContainerLowestDarkHighContrast
            surfaceContainerLow shouldBe surfaceContainerLowDarkHighContrast
            surfaceContainer shouldBe surfaceContainerDarkHighContrast
            surfaceContainerHigh shouldBe surfaceContainerHighDarkHighContrast
            surfaceContainerHighest shouldBe surfaceContainerHighestDarkHighContrast
        }
    }
}
