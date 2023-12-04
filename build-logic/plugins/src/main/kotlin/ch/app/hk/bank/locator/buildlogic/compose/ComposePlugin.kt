package ch.app.hk.bank.locator.buildlogic.compose

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
            extension.buildFeatures {
                compose = true
            }

            extension.composeOptions {
                kotlinCompilerExtensionVersion = "1.5.2"
            }

            project.dependencies {
                val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
                add("implementation", composeBom)
                add("implementation", "androidx.compose.runtime:runtime")
                add("androidTestImplementation", composeBom)
            }
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            it.kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + project.buildComposeMetricsParameters()
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