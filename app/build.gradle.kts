plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.compose")
}

android {
    namespace = "ch.app.hk.bank.locator"

    defaultConfig {
        applicationId = "ch.app.hk.bank.locator"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
