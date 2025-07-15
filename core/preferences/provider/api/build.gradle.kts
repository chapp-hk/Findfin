plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.app.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
