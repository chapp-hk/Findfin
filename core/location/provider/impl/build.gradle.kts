plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "org.chapp.findfin.core.location.provider.impl"

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
    implementation(libs.play.services.base)
    implementation(libs.play.services.location)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(projects.core.location.provider.api)
    implementation(projects.core.logging.api)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(projects.testing.googlePlayServicesTasks)

    // TODO - Remove this dependency
    androidTestImplementation(libs.androidx.test.runner)
}
