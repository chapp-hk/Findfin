package ch.app.hk.bank.locator.buildlogic.plugin.hilt

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltAndroidTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        @Suppress("UnstableApiUsage")
        project
            .extensions
            .getByType(AndroidComponentsExtension::class.java)
            .finalizeDsl { extension ->
                extension.defaultConfig {
                    testInstrumentationRunner = "ch.app.hk.bank.locator.testing.instrument.AppTestRunner"
                }
            }

        project.dependencies {
            "debugImplementation"(project(mapOf("path" to ":testing:instrument")))

            "androidTestImplementation"("com.google.dagger:hilt-android-testing:2.50")
            "kspAndroidTest"("com.google.dagger:hilt-android-compiler:2.50")
        }
    }
}
