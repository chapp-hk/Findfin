package org.chapp.findfin.buildlogic.plugin.mapstruct

import org.chapp.findfin.buildlogic.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MapStructPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("org.jetbrains.kotlin.kapt")

            dependencies {
                "implementation"(libs.findLibrary("mapstruct").get())
                "kapt"(libs.findLibrary("mapstruct-processor").get())
            }
        }
    }
}
