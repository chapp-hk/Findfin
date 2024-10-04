plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.google.gms.services)
}

android {
    namespace = "org.chapp.findfin"

    defaultConfig {
        applicationId = "org.chapp.findfin"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    androidResources {
        @Suppress("UnstableApiUsage")
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(projects.core.design.ui)
    implementation(projects.core.navigation)
    implementation(projects.feature.onboarding.navigation)
    implementation(projects.feature.auth.navigation)
    implementation(projects.feature.home.navigation)
    implementation(projects.feature.setting.data.repo)
    implementation(projects.core.logging.api)
    implementation(projects.core.logging.startup)

    implementation(libs.androidx.core.ktx)
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(projects.testing.extension)
}
