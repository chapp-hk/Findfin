plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.hilt.android.test)
    alias(libs.plugins.app.mapstruct)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "org.chapp.findfin.feature.onboarding.presentation"
    resourcePrefix = "onboarding_"

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
    implementation(projects.core.design.uiFoundation)
    implementation(projects.core.locale.api)
    implementation(projects.core.preferences.provider)
    implementation(projects.feature.bank.data.localDatabase)
    implementation(projects.feature.bank.data.remoteNetwork)
    implementation(projects.feature.setting.domain)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.android.lottie.compose)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(projects.testing.extension)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // todo: investigate to remove this dependency
    androidTestImplementation(projects.core.locale.impl)
}
