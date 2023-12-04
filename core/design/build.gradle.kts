plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
}

android {
    namespace = "ch.app.hk.bank.locator.core.design"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
}
