plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "org.chapp.findfin.feature.bank.presentation"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.bank.ui)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
