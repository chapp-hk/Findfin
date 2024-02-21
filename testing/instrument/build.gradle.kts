plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.hilt.android")
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
