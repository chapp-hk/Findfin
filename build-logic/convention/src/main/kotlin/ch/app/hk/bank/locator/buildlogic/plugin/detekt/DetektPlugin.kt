package org.chapp.findfin.buildlogic.plugin.detekt

import org.chapp.findfin.buildlogic.util.assertRootProjectAppliedPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class DetektPlugin : Plugin<Project> {
    private val detektGradlePluginId = "io.gitlab.arturbosch.detekt"

    override fun apply(project: Project) {
        project.assertRootProjectAppliedPlugin(pluginId = "app.plugin.detekt")
        project.plugins.apply(detektGradlePluginId)
        configureSubProjects(rootProject = project)
    }

    private fun configureSubProjects(rootProject: Project) {
        rootProject
            .subprojects
            .filter { it.buildFile.exists() }
            .forEach{
                it.apply(plugin = detektGradlePluginId)
            }
    }
}
