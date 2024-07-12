plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "ch.app.hk.bank.locator.feature.onboarding.navigation"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.feature.onboarding.ui)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
