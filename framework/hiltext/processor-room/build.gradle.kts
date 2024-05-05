plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(projects.framework.hiltext.annotation)
    implementation(projects.framework.hiltext.util)

    val kotlinPoetVersion = "1.16.0"
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.23-1.0.20")
    implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
    implementation("com.google.dagger:hilt-core:2.51.1")
    implementation("androidx.room:room-common:2.6.1")

    testImplementation(projects.testing.util)
    kspTest(projects.framework.hiltext.processorRoom)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")
}
