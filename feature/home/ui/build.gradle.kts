plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.hilt.android.test)
}

android {
    namespace = "ch.app.hk.bank.locator.feature.home.ui"
    resourcePrefix = "home_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging {
        resources.excludes +=
            setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
    }
}

dependencies {
    implementation(projects.core.design.ui)
    implementation(projects.core.navigation)
    implementation(projects.core.location.impl)
    implementation(projects.core.location.launcher)
    implementation(projects.feature.locator.data.localDatabase)
    implementation(projects.feature.auth.data.repo)
    implementation(projects.feature.auth.data.remoteFirebase)
    implementation(projects.feature.home.domain)
    // TODO - see is it possible to expose navigation module for bottomsheet
    implementation(projects.feature.locator.ui)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(projects.testing.extension)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
