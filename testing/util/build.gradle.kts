plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

dependencies {
    implementation(libs.ktor.serialization.json)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.serialization.json)
}
