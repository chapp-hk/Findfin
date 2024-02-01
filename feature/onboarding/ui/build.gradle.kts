plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
    id("app.plugin.mapstruct")
    id("app.plugin.hilt.android")
    id("app.plugin.kover.android")
}

android {
    namespace = "ch.app.hk.bank.locator.feature.onboarding.ui"
    resourcePrefix = "onboarding_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:design")))
    implementation(project(mapOf("path" to ":core:preferences:api")))
    implementation(project(mapOf("path" to ":core:preferences:impl")))
    implementation(project(mapOf("path" to ":core:locale:api")))
    implementation(project(mapOf("path" to ":core:locale:impl")))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.hilt.compose)

    testImplementation(project(mapOf("path" to ":testing:extension")))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.cash.app.turbine)

    androidTestImplementation(libs.androidx.test.runner)
}
