plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.locale.impl"
    resourcePrefix = "locale_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.locale.api)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.assertions.core)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotest.assertions.core)
}
