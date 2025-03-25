package org.chapp.findfin.buildlogic.plugin.android

import com.android.build.api.variant.AndroidComponentsExtension
import org.chapp.findfin.buildlogic.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        @Suppress("UnstableApiUsage")
        androidComponents.finalizeDsl { extension ->
            extension.defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            extension.buildTypes {
                getByName("debug") {
                    it.enableUnitTestCoverage = true
                    it.enableAndroidTestCoverage = true
                }
            }

            extension.testOptions {
                managedDevices {
                    localDevices.create("pixel8api34") {
                        // Use device profiles you typically see in Android Studio.
                        it.device = "Pixel 8"
                        // Use only API levels 27 and higher.
                        it.apiLevel = 34
                        // To include Google services, use "google".
                        it.systemImageSource = "aosp"
                    }
                }

                unitTests.all { test ->
                    test.useJUnitPlatform()
                    test.testLogging {
                        it.events("passed", "skipped", "failed")
                    }
                }

                unitTests.isReturnDefaultValues = true
            }

            project.dependencies {
                "testRuntimeOnly"(project.libs.findLibrary("junit-platform-launcher").get())
            }
        }
    }
}
