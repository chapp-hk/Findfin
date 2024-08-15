plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(projects.library.hiltwrap.annotation)
    implementation(projects.library.hiltwrap.util)
    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.dagger.hilt.core)

    kspTest(projects.library.hiltwrap.processorBinds)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.compile.testing.ksp)
}
