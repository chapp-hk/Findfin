plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.testing.util)
    implementation(libs.ktor.client.mock)
    implementation(libs.ktor.client.logging)
    implementation(libs.androidx.annotation)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
}
