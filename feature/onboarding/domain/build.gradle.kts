plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
    id("app.plugin.hilt.jvm")
}

dependencies {
    implementation(project(mapOf("path" to ":core:threading")))
    implementation(project(mapOf("path" to ":core:locale:api")))
    implementation(project(mapOf("path" to ":feature:locator:data:repo")))
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
