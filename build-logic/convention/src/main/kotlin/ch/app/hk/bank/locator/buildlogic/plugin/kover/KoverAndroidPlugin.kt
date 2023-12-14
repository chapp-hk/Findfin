package ch.app.hk.bank.locator.buildlogic.plugin.kover

import com.android.build.api.variant.AndroidComponentsExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverAndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions
        @Suppress("UnstableApiUsage")
        project.extensions
            .getByType(AndroidComponentsExtension::class.java)
            .finalizeDsl {
                project.extensions.configure(KoverReportExtension::class) {
                    it.defaults { kover ->
                        kover.mergeWith("debug")
                    }
                }
            }
    }
}
