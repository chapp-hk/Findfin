plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
