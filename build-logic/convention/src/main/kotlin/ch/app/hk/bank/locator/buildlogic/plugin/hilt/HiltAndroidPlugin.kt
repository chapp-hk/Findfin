package ch.app.hk.bank.locator.buildlogic.plugin.hilt

import ch.app.hk.bank.locator.buildlogic.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltAndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(libs.findLibrary("google-dagger-hilt-android").get())
                "ksp"(libs.findLibrary("google-dagger-hilt-compiler").get())

                "implementation"(project(mapOf("path" to ":framework:hiltext:annotation")))
                "ksp"(project(mapOf("path" to ":framework:hiltext:processor-binds")))
            }
        }
    }
}
