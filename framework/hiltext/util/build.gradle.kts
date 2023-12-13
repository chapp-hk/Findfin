plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
}

dependencies {
    val kotlinPoetVersion = "1.14.2"

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.14")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}
