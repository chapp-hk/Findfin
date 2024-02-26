// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false

    // convention plugins
    alias(libs.plugins.app.ktlint)
    alias(libs.plugins.app.detekt)
    alias(libs.plugins.app.kover)
    alias(libs.plugins.app.jvm) apply false
    alias(libs.plugins.app.kover.android) apply false
    alias(libs.plugins.app.android.common) apply false
    alias(libs.plugins.app.compose) apply false
    alias(libs.plugins.app.hilt.android) apply false
    alias(libs.plugins.app.hilt.android.test) apply false
    alias(libs.plugins.app.hilt.jvm) apply false
    alias(libs.plugins.app.room.android) apply false
    alias(libs.plugins.app.mapstruct) apply false
}
