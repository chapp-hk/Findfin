package ch.app.hk.bank.locator.buildlogic.kover

import ch.app.hk.bank.locator.buildlogic.assertRootProjectAppliedPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KoverPlugin : Plugin<Project> {
    private val koverGradlePluginId = "org.jetbrains.kotlinx.kover"
    override fun apply(project: Project) {
        project.assertRootProjectAppliedPlugin(pluginId = "app.plugin.kover")
        project.plugins.apply(koverGradlePluginId)
        subProjectsApplyKoverPlugins(rootProject = project)
        configureKoverDependencies(rootProject = project)
    }

    private fun subProjectsApplyKoverPlugins(rootProject: Project) {
        rootProject.subprojects {
            it.apply(plugin = koverGradlePluginId)
        }
    }

    private fun configureKoverDependencies(rootProject: Project) {
        val appProject = rootProject.subprojects.find { it.path == ":app" }!!
        appProject.dependencies {
            rootProject
                .subprojects
                .filter { it.buildFile.exists() }
                .forEach {
                    add("kover", project(mapOf("path" to it.path)))
                }
        }
    }
}
