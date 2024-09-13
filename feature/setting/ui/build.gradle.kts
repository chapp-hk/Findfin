plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
}

android {
    namespace = "ch.app.hk.bank.locator.feature.setting.ui"
    resourcePrefix = "setting_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
}
