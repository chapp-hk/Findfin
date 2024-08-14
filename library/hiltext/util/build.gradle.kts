plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinpoet.ksp)
}
