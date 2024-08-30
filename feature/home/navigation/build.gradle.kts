plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "ch.app.hk.bank.locator.feature.home.navigation"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.home.ui)
    implementation(projects.feature.auth.navigation)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.kotlinx.serialization.json)

    // TODO - see is it possible to expose navigation module for bottomsheet
    implementation(projects.feature.bank.ui)
    implementation(projects.feature.locator.ui)
}
