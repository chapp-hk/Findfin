plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.room.android)
    alias(libs.plugins.app.mapstruct)
}

android {
    namespace = "org.chapp.findfin.feature.bank.data.local.database"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.threading)
    implementation(projects.feature.bank.data.repo)
    implementation(projects.core.logging.api)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.androidx.test.runner)
}
