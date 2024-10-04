plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "org.chapp.findfin.feature.home.navigation"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.home.ui)
    implementation(projects.feature.auth.navigation)
    implementation(projects.feature.bank.navigation)
    implementation(projects.feature.locator.navigation)
    implementation(projects.feature.setting.navigation)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.kotlinx.serialization.json)
}
