plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.mapstruct)
    id("kotlin-parcelize")
}

android {
    namespace = "ch.app.hk.bank.locator.core.design.ui"
    resourcePrefix = "core_ui_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.design.theme)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
