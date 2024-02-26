plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
    alias(libs.plugins.app.kover.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.locale.impl"
    resourcePrefix = "locale_"

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
    implementation(projects.core.locale.api)
    implementation(libs.androidx.appcompat)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.kotest.assertions.core)
}
