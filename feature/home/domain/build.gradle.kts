plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
    alias(libs.plugins.app.hilt.jvm)
    alias(libs.plugins.app.mapstruct)
}

dependencies {
    implementation(projects.core.threading)
    implementation(projects.core.location.api)
    implementation(projects.core.logging.api)
    implementation(projects.feature.locator.data.repo)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.assertions.core)
}
