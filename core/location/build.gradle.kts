plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.kover.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.location"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
