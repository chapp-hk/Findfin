plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
}

dependencies {
    implementation(libs.kermit)
    testImplementation(libs.kermit.test)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.assertions.core)
}
