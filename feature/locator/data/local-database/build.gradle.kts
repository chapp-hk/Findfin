plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    id("app.plugin.android.common")
    id("app.plugin.hilt.android")
    id("app.plugin.mapstruct")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.locator.data.local.database"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    val roomVersion = "2.6.1"

    implementation(project(mapOf("path" to ":feature:locator:data:local")))
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}

// TODO - move this config to convention plugin
koverReport {
    defaults {
        mergeWith("debug")
    }
}
