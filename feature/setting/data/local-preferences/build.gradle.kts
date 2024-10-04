plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "org.chapp.findfin.feature.setting.data.local.preferences"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.preferences.storage)
    implementation(projects.feature.setting.data.repo)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    // TODO - Remove this dependency
    androidTestImplementation(libs.androidx.test.runner)
}
