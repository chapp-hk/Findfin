plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "org.chapp.findfin.core.preferences.provider.impl"

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
    implementation(projects.core.preferences.provider.api)
    implementation(projects.core.logging.api)
    implementation(projects.core.threading)
    implementation(libs.androidx.datastore.preferences)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.mockk.android)
}
