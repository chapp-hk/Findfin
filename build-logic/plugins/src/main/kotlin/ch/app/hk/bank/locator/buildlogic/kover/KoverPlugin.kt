package ch.app.hk.bank.locator.buildlogic.kover

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KoverPlugin : Plugin<Project> {
    private val koverGradlePluginId = "org.jetbrains.kotlinx.kover"
    override fun apply(project: Project) {
        assertRootProject(rootProject = project)
        project.plugins.apply(koverGradlePluginId)
        subProjectsApplyKoverPlugins(rootProject = project)
        configureKoverDependencies(rootProject = project)
    }

    private fun assertRootProject(rootProject: Project) {
        if (rootProject.rootProject !== rootProject) {
            throw GradleException(
                "The \"app.plugin.kover\" plugin cannot be applied to project '${rootProject.name}'" +
                    "because it is not the root project. Build file: ${rootProject.buildFile}"
            )
        }
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
                    println(it.path)
                    add("kover", project(mapOf("path" to it.path)))
                }
        }
    }
}
