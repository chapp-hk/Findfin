package org.chapp.findfin.buildlogic.plugin.hilt

import org.chapp.findfin.buildlogic.util.libs
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltAndroidTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            extensions
                .getByType(AndroidComponentsExtension::class.java)
                .finalizeDsl { extension ->
                    extension.defaultConfig {
                        testInstrumentationRunner = "org.chapp.findfin.testing.instrument.AppTestRunner"
                    }
                }

            dependencies {
                "debugImplementation"(project(mapOf("path" to ":testing:instrument")))

                "androidTestImplementation"(libs.findLibrary("dagger-hilt-android-testing").get())
                "kspAndroidTest"(libs.findLibrary("dagger-hilt-compiler").get())
            }
        }
    }
}
