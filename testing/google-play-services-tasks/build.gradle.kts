plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
}

android {
    namespace = "ch.app.hk.bank.locator.testing.google.play.services.task"

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
    implementation(libs.play.services.tasks)
    implementation(libs.mockk.android)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}
