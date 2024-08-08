import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
    id("org.jetbrains.dokka")
}

android {
    namespace = "org.chapp.findfin.core.locale.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(projects.core.locale.api)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.assertions.core)

    androidTestImplementation(libs.androidx.test.runner)
}

tasks.withType<DokkaTaskPartial>().configureEach {
    moduleName = project.path
    dokkaSourceSets {
        configureEach {
            documentedVisibilities.add(DokkaConfiguration.Visibility.INTERNAL)
        }
    }
}
