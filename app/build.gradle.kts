plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
    id("app.plugin.hilt.android")
}

android {
    namespace = "ch.app.hk.bank.locator"

    defaultConfig {
        applicationId = "ch.app.hk.bank.locator"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    androidResources {
        @Suppress("UnstableApiUsage")
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:design")))
    implementation(project(mapOf("path" to ":core:navigation")))
    implementation(project(mapOf("path" to ":feature:onboarding:navigation")))

    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
