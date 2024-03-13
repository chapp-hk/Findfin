plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.com.google.gms.services)
}

android {
    namespace = "ch.app.hk.bank.locator"

    defaultConfig {
        applicationId = "ch.app.hk.bank.locator"
        targetSdk = 34
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
    implementation(projects.core.theme)
    implementation(projects.core.navigation)
    implementation(projects.feature.onboarding.navigation)
    implementation(projects.feature.auth.navigation)
    implementation(projects.feature.home.navigation)

    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
}
