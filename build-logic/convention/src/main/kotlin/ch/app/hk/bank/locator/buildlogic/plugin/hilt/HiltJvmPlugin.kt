package ch.app.hk.bank.locator.buildlogic.plugin.hilt

import ch.app.hk.bank.locator.buildlogic.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltJvmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(libs.findLibrary("dagger-hilt-core").get())
                "ksp"(libs.findLibrary("dagger-hilt-compiler").get())

                "implementation"(project(":framework:hiltext:annotation"))
                "ksp"(project(":framework:hiltext:processor-binds"))
            }
        }
    }
}
