plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.com.google.devtools.ksp)
    id("app.plugin.jvm")
}

dependencies {
    val kotlinPoetVersion = "1.16.0"

    implementation(project(mapOf("path" to ":framework:hiltext:annotation")))
    implementation(project(mapOf("path" to ":framework:hiltext:util")))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")
    implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
    implementation("com.google.dagger:hilt-core:2.50")

    kspTest(project(mapOf("path" to ":framework:hiltext:processor-binds")))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")
}
