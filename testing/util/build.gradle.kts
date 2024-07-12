plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

dependencies {
    implementation(libs.ktor.serialization.kotlinx.json)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.serialization.json)
}
