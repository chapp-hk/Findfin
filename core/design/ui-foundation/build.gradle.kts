import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.mapstruct)
    id("kotlin-parcelize")
    id("org.jetbrains.dokka")
}

android {
    namespace = "org.chapp.findfin.core.design.ui.foundation"
    resourcePrefix = "core_ui_"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.design.theme)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

tasks.withType<DokkaTaskPartial>().configureEach {
    moduleName = project.path
}
