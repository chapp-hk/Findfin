plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.hilt.android.test)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "org.chapp.findfin.feature.locator.presentation"
    resourcePrefix = "locator_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.location)
    implementation(projects.core.navigation)
    implementation(projects.core.map)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
