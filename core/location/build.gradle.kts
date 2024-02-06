plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
}

android {
    namespace = "ch.app.hk.bank.locator.core.location"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
