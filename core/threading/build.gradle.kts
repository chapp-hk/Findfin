plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
    id("app.plugin.hilt.jvm")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}
