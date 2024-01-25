plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
    id("app.plugin.hilt.android")
    id("app.plugin.kover.android")
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
    implementation(project(mapOf("path" to ":core:locale:api")))
    implementation(libs.androidx.appcompat)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.kotest.assertions.core)
}
