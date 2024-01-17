plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.hilt.android")
    id("app.plugin.kover.android")
}

android {
    namespace = "ch.app.hk.bank.locator.core.preferences.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging {
        resources.excludes += "META-INF/LICENSE.md"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:preferences:api")))
    implementation(libs.androidx.datastore.preferences)
}
