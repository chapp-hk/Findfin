plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.hilt.android.test)
}

android {
    namespace = "ch.app.hk.bank.locator.feature.setting.ui"
    resourcePrefix = "setting_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.preferences.uiFoundation)
    implementation(projects.feature.setting.data.repo)
    implementation(projects.feature.setting.data.localPreferences)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
