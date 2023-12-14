package ch.app.hk.bank.locator.buildlogic.plugin.mapstruct

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MapStructPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.kapt")

        val mapStructVersion = "1.6.0.Beta1"
        project.dependencies {
            "implementation"("org.mapstruct:mapstruct:$mapStructVersion")
            "kapt"("org.mapstruct:mapstruct-processor:$mapStructVersion")
        }
    }
}
