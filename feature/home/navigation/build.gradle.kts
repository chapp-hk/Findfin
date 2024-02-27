plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
}
