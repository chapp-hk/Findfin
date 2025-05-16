// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.benchmark) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.google.gms.services) apply false
    alias(libs.plugins.google.secrets)

    // convention plugins
    alias(libs.plugins.app.ktlint)
    alias(libs.plugins.app.detekt)
    alias(libs.plugins.app.jvm) apply false
    alias(libs.plugins.app.android.common) apply false
    alias(libs.plugins.app.compose) apply false
    alias(libs.plugins.app.hilt.android) apply false
    alias(libs.plugins.app.hilt.android.test) apply false
    alias(libs.plugins.app.hilt.jvm) apply false
    alias(libs.plugins.app.room.android) apply false
    alias(libs.plugins.app.mapstruct) apply false
    alias(libs.plugins.app.jacoco)
}
