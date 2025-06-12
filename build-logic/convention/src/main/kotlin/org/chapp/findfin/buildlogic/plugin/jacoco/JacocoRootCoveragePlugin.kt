package org.chapp.findfin.buildlogic.plugin.jacoco

import org.chapp.findfin.buildlogic.util.assertRootProjectAppliedPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoRootCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            assertRootProjectAppliedPlugin(pluginId = "JacocoRootCoveragePlugin")

            pluginManager.apply(JACOCO_PLUGIN_ID)

            configureSubProjects(project)
            configureRootJacocoTask(project)
        }
    }

    private fun configureSubProjects(project: Project) {
        val jacocoLocalProperties = project.getJacocoLocalProperties()

        project.subprojects { subproject ->
            subproject.afterEvaluate {
                val androidPlugin = it.plugins.hasPlugin("kotlin-android")
                val jvmPlugin = it.plugins.hasPlugin("org.jetbrains.kotlin.jvm")

                if (jvmPlugin) {
                    it.pluginManager.apply(JACOCO_PLUGIN_ID)
                    it.tasks.withType<JacocoReport> {
                        configureJvmJacocoTask()
                    }
                } else if (androidPlugin) {
                    it.pluginManager.apply(JACOCO_PLUGIN_ID)
                    it.tasks.register<JacocoReport>(JACOCO_REPORT_TASK_NAME) {
                        configureAndroidJacocoTask(
                            buildVariant = jacocoLocalProperties.buildVariant,
                            instrumentTestType = jacocoLocalProperties.instrumentTestType,
                            gradleManagedDeviceName = jacocoLocalProperties.gradleManagedDeviceName,
                        )
                    }
                }
            }
        }
    }

    private fun JacocoReport.configureJvmJacocoTask() {
        dependsOn(project.tasks.withType<Test>())
        configReports()

        classDirectories.setFrom(
            project.fileTree("${project.layout.buildDirectory.asFile.get()}/classes/kotlin/main") {
                it.exclude(excludeClassed)
            }
        )
    }

    private fun JacocoReport.configureAndroidJacocoTask(
        buildVariant: String,
        instrumentTestType: InstrumentTestType,
        gradleManagedDeviceName: String,
    ) {
        dependsOn(
            project.getAndroidJacocoTaskDependencies(
                buildVariant = buildVariant,
                instrumentTestType = instrumentTestType,
                gradleManagedDeviceName = gradleManagedDeviceName
            )
        )

        configReports()

        classDirectories.setFrom(
            project.fileTree("${project.layout.buildDirectory.asFile.get()}/tmp/kotlin-classes/$buildVariant") {
                it.exclude(excludeClassed)
            }
        )

        sourceDirectories.setFrom(project.getSourceDirectories())

        executionData.setFrom(
            project.fileTree("${project.layout.buildDirectory.asFile.get()}/outputs") {
                it.include(
                    "**/*.ec",
                    "**/*.exec",
                )
            }
        )
    }

    private fun configureRootJacocoTask(rootProject: Project) {
        rootProject.tasks.register<JacocoReport>("rootJacocoCoverageReport") {
            rootProject.subprojects { subproject ->
                val subprojectJacoco = subproject.tasks.findByName(JACOCO_REPORT_TASK_NAME) as? JacocoReport
                if (subprojectJacoco != null) {
                    dependsOn(subprojectJacoco)
                    classDirectories.setFrom(classDirectories + subprojectJacoco.classDirectories)
                    sourceDirectories.setFrom(sourceDirectories + subprojectJacoco.sourceDirectories)
                    executionData.setFrom(executionData + subprojectJacoco.executionData)
                }
            }
            configReports()
        }
    }

    private fun JacocoReport.configReports() {
        reports {
            it.xml.required.set(true)
            it.html.required.set(true)
        }
    }

    private fun Project.getAndroidJacocoTaskDependencies(
        buildVariant: String,
        instrumentTestType: InstrumentTestType,
        gradleManagedDeviceName: String,
    ): Array<String> {
        val uppercaseBuildVariant = buildVariant.uppercaseFirstChar()

        val unitTestPath = "test${uppercaseBuildVariant}UnitTest"
        val androidTestPath =
            if (hasAndroidTest()) {
                when (instrumentTestType) {
                    InstrumentTestType.CONNECTED_DEVICE ->
                        "connected${uppercaseBuildVariant}AndroidTest"

                    InstrumentTestType.GRADLE_MANAGED_DEVICE ->
                        "${gradleManagedDeviceName}${uppercaseBuildVariant}AndroidTest"
                }
            } else {
                null
            }

        return listOfNotNull(unitTestPath, androidTestPath).toTypedArray()
    }

    private fun Project.getSourceDirectories(): String {
        return "${layout.projectDirectory.asFile.path}/src/main/kotlin"
    }

    private fun Project.hasAndroidTest(): Boolean {
        val androidTestDir = file("${layout.projectDirectory.asFile.path}/src/androidTest")
        return androidTestDir.exists() && androidTestDir.isDirectory
    }

    companion object {
        private const val JACOCO_PLUGIN_ID = "jacoco"
        private const val JACOCO_REPORT_TASK_NAME = "jacocoTestReport"
    }
}
