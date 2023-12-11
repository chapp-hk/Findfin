plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization") version "1.9.20"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:threading")))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.android)

    testImplementation(project(mapOf("path" to ":testing:util")))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.ktor.mock)
    testImplementation(libs.ktor.logging)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
}
