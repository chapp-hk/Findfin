plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
    id("app.plugin.hilt.jvm")
    id("app.plugin.mapstruct")
}

dependencies {
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":feature:locator:data:local")))
    implementation(project(mapOf("path" to ":feature:locator:data:remote")))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
