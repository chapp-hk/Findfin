plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.threading)
    implementation(projects.core.logging.api)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.android)

    testImplementation(projects.testing.util)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.ktor.mock)
    testImplementation(libs.ktor.logging)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
}
