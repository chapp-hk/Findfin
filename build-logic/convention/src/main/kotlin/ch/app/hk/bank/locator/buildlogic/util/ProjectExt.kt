package ch.app.hk.bank.locator.buildlogic.util

import org.gradle.api.GradleException
import org.gradle.api.Project

fun Project.assertRootProjectAppliedPlugin(pluginId: String) {
    if (this.rootProject !== this) {
        throw GradleException(
            "The \"$pluginId\" plugin cannot be applied to project '${rootProject.name}'" +
                "because it is not the root project. Build file: ${rootProject.buildFile}"
        )
    }
}
