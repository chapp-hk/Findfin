plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.konsist)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.ktor.client.resources)
}
