plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
}

dependencies {
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.cash.app.turbine)
}
