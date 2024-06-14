plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    val kotlinPoetVersion = "1.17.0"

    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.22")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}
