plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    val kotlinPoetVersion = "1.16.0"

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}
