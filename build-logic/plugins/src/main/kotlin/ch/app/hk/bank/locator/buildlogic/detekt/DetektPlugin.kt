package ch.app.hk.bank.locator.buildlogic.detekt

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class DetektPlugin : Plugin<Project> {
    private val detektGradlePluginId = "io.gitlab.arturbosch.detekt"

    override fun apply(project: Project) {
        assertRootProject(rootProject = project)
        project.plugins.apply(detektGradlePluginId)
        configureSubProjects(rootProject = project)
    }

    private fun assertRootProject(rootProject: Project) {
        if (rootProject.rootProject !== rootProject) {
            throw GradleException(
                "The \"app.plugin.detekt\" plugin cannot be applied to project '${rootProject.name}'" +
                        "because it is not the root project. Build file: ${rootProject.buildFile}"
            )
        }
    }

    private fun configureSubProjects(rootProject: Project) {
        rootProject.subprojects {
            it.apply(plugin = detektGradlePluginId)
        }
    }
}