plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.lemon.app.dev.konsist)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.ktor.resources)
}
