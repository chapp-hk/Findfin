plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.plugin.android.common")
}

android {
    namespace = "ch.app.hk.bank.locator.core.locale"
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
    implementation(libs.androidx.appcompat)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.kotest.assertions.core)
}
