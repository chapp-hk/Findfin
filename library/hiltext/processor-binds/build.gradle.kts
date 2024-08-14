plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(projects.library.hiltext.annotation)
    implementation(projects.library.hiltext.util)
    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.dagger.hilt.core)

    kspTest(projects.library.hiltext.processorBinds)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.compile.testing.ksp)
}
