plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
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
    implementation(libs.google.play.services.tasks)
    implementation(libs.mockk.android)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
}
