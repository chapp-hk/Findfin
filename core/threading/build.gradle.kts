plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}
