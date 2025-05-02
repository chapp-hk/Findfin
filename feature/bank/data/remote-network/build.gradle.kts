plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.threading)
    implementation(projects.core.logging.api)
    implementation(projects.feature.bank.data.repo)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.client.android)

    testImplementation(projects.testing.util)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.client.logging)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
}
