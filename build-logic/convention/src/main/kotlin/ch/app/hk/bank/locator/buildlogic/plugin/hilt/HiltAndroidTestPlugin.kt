package ch.app.hk.bank.locator.buildlogic.plugin.hilt

import ch.app.hk.bank.locator.buildlogic.util.libs
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
                        testInstrumentationRunner = "ch.app.hk.bank.locator.testing.instrument.AppTestRunner"
                    }
                }

            dependencies {
                "debugImplementation"(project(mapOf("path" to ":testing:instrument")))

                "androidTestImplementation"(libs.findLibrary("google-dagger-hilt-android-testing").get())
                "kspAndroidTest"(libs.findLibrary("google-dagger-hilt-compiler").get())
            }
        }
    }
}
