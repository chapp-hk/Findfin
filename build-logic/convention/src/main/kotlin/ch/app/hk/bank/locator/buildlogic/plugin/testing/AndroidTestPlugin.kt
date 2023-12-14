package ch.app.hk.bank.locator.buildlogic.plugin.testing

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        @Suppress("UnstableApiUsage")
        androidComponents.finalizeDsl { extension ->
            extension.buildTypes {
                getByName("debug") {
                    it.enableUnitTestCoverage = true
                    it.enableAndroidTestCoverage = true
                }
            }

            extension.testOptions {
                managedDevices {
                    localDevices.create("pixel2api30") {
                        // Use device profiles you typically see in Android Studio.
                        it.device = "Pixel 2"
                        // Use only API levels 27 and higher.
                        it.apiLevel = 30
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
            }
        }
    }
}
