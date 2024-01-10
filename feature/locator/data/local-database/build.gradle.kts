plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.kover.android")
    id("app.plugin.hilt.android")
    id("app.plugin.room.android")
    id("app.plugin.mapstruct")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.locator.data.local.database"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":feature:locator:data:local")))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}
