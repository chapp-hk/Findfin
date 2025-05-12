package org.chapp.findfin.buildlogic.plugin.compose

import com.android.build.api.dsl.CommonExtension
import org.chapp.findfin.buildlogic.util.libs
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class ComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.finalizeDsl { extension ->
            val ext = extension as CommonExtension<*, *, *, *, *, *>
            with(project) {
                pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

                ext.buildFeatures {
                    compose = true
                }

                dependencies {
                    val bom = libs.findLibrary("androidx-compose-bom").get()
                    "implementation"(platform(bom))
                    "androidTestImplementation"(platform(bom))
                    "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                    "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
                }
            }
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            it.compilerOptions {
                freeCompilerArgs.addAll(project.buildComposeMetricsParameters())
            }
        }
    }

    private fun Project.buildComposeMetricsParameters(): List<String> {
        val buildDir = project.layout.buildDirectory.asFile.get()

        val metricParameters = mutableListOf<String>()
        val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
        val enableMetrics = (enableMetricsProvider.orNull == "true")
        if (enableMetrics) {
            val metricsFolder = File(buildDir, "compose-metrics")
            metricParameters.add("-P")
            metricParameters.add(
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
            )
        }

        val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
        val enableReports = (enableReportsProvider.orNull == "true")
        if (enableReports) {
            val reportsFolder = File(buildDir, "compose-reports")
            metricParameters.add("-P")
            metricParameters.add(
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
            )
        }
        return metricParameters.toList()
    }
}
