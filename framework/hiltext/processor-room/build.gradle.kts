plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(projects.framework.hiltext.annotation)
    implementation(projects.framework.hiltext.util)

    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.dagger.hilt.core)
    implementation(libs.androidx.room.common)

    testImplementation(projects.testing.util)
    kspTest(projects.framework.hiltext.processorRoom)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.compile.testing.ksp)
}
