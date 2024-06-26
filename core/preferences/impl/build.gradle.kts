plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.preferences.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.preferences.api)
    implementation(projects.core.threading)
    implementation(libs.androidx.datastore.preferences)

    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.cash.app.turbine)
    androidTestImplementation(libs.androidx.test.runner)
}
