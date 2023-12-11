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
    implementation(libs.ktor.serialization.json)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
}
