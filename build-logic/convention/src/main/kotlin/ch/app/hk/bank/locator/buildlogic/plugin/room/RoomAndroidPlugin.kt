package ch.app.hk.bank.locator.buildlogic.plugin.room

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomAndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                val roomVersion = "2.6.1"

                "implementation"("androidx.room:room-runtime:$roomVersion")
                "implementation"("androidx.room:room-ktx:$roomVersion")
                "ksp"("androidx.room:room-compiler:$roomVersion")
            }
        }
    }
}
