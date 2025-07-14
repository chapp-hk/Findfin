plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
    alias(libs.plugins.app.mapstruct)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.threading)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
