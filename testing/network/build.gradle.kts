plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.testing.util)
    implementation(libs.ktor.mock)
    implementation(libs.ktor.logging)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
}
