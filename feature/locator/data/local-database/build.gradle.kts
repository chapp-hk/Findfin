plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    id("app.plugin.android.common")
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
    ksp("androidx.room:room-compiler:$roomVersion")
}
