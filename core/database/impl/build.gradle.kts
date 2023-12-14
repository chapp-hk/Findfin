plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    id("app.plugin.android.common")
    id("app.plugin.hilt.android")
}

android {
    namespace = "ch.app.hk.bank.locator.core.database.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:database:api")))
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
}
