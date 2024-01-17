plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("app.plugin.jvm")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
