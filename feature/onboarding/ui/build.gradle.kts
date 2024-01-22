plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
    id("app.plugin.hilt.android")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.onboarding.ui"
    resourcePrefix = "onboarding_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:preferences:api")))
    implementation(project(mapOf("path" to ":core:preferences:impl")))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.hilt.compose)
}
