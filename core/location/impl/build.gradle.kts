plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.kover.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.location.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.google.play.services.base)
    implementation(libs.google.play.services.location)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(projects.core.threading)
    implementation(projects.core.location.api)
    implementation(projects.core.logging.api)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(projects.testing.googlePlayServicesTasks)
}
