package org.chapp.findfin.buildlogic.plugin.jvm

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class JvmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(JavaPluginExtension::class.java) {
            it.sourceCompatibility = JavaVersion.VERSION_17
            it.targetCompatibility = JavaVersion.VERSION_17
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            it.compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
                freeCompilerArgs.addAll(
                    listOf("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
                )
            }
        }

        project.tasks.withType<Test>().configureEach { test ->
            test.useJUnitPlatform()
            test.testLogging {
                it.events("passed", "skipped", "failed")
            }
        }
    }
}
