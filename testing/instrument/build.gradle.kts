plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.kover.android)
}

android {
    namespace = "ch.app.hk.bank.locator.testing.instrument"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.test.runner)
    implementation("com.google.dagger:hilt-android-testing:2.50")
}
