plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.onboarding.navigation"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.onboarding.ui)

    implementation(libs.androidx.navigation.compose)
}
