package org.chapp.findfin.buildlogic.util

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.assertRootProjectAppliedPlugin(pluginId: String) {
    if (this.rootProject !== this) {
        throw GradleException(
            "The \"$pluginId\" plugin cannot be applied to project '${rootProject.name}'" +
                "because it is not the root project. Build file: ${rootProject.buildFile}"
        )
    }
}
