plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
    id("app.plugin.mapstruct")
    id("app.plugin.hilt.android")
    id("app.plugin.hilt.android.test")
    id("app.plugin.kover.android")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.onboarding.ui"
    resourcePrefix = "onboarding_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging {
        resources.excludes +=
            setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
    }
}

dependencies {
    implementation(projects.core.design)
    implementation(projects.core.preferences.api)
    implementation(projects.core.preferences.impl)
    implementation(projects.core.location)
    implementation(projects.core.locale.api)
    implementation(projects.core.locale.impl)
    implementation(projects.feature.locator.data.localDatabase)
    implementation(projects.feature.onboarding.domain)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.hilt.compose)
    implementation(libs.airbnb.lottie.compose)

    testImplementation(projects.testing.extension)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.cash.app.turbine)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
