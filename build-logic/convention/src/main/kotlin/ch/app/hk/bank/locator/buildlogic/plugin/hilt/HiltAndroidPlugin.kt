package ch.app.hk.bank.locator.buildlogic.plugin.hilt

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
                "implementation"("com.google.dagger:hilt-android:2.48")
                "ksp"("com.google.dagger:hilt-compiler:2.48")

                "implementation"(project(mapOf("path" to ":framework:hiltext:annotation")))
                "ksp"(project(mapOf("path" to ":framework:hiltext:processor-binds")))
            }
        }
    }
}
