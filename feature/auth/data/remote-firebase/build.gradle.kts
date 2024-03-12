plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.kover.android)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.hilt.android.test)
}

android {
    namespace = "ch.app.hk.bank.locator.feature.auth.data.remote.firebase"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.threading)
    implementation(projects.feature.auth.data.remote)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.kotlinx.coroutines.play.services)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(projects.testing.googlePlayServicesTasks)
}
