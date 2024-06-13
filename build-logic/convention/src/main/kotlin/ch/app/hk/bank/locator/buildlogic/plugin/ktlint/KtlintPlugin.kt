package ch.app.hk.bank.locator.buildlogic.plugin.ktlint

import ch.app.hk.bank.locator.buildlogic.util.assertRootProjectAppliedPlugin
import ch.app.hk.bank.locator.buildlogic.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintPlugin : Plugin<Project> {
    private val ktlintGradlePluginId = "org.jlleitschuh.gradle.ktlint"

    override fun apply(project: Project) {
        project.assertRootProjectAppliedPlugin(pluginId = "app.plugin.ktlint")
        project.plugins.apply(ktlintGradlePluginId)
        configureSubProjects(rootProject = project)
        configureGitHook(rootProject = project)
    }

    private fun configureSubProjects(rootProject: Project) {
        rootProject
            .subprojects
            .filter { it.buildFile.exists() }
            .forEach {
                it.apply(plugin = ktlintGradlePluginId)

                // configure plugin
                it.configure<KtlintExtension> {
                    version.set(rootProject.libs.findVersion("ktlint").get().displayName)
                    debug.set(true)

                    filter { pattern ->
                        pattern.exclude(
                            "**/*HiltExtBindModule.kt",
                        )
                    }
                }
            }
    }

    private fun configureGitHook(rootProject: Project) {
        rootProject.tasks.register("copyGitHooks", Copy::class) {
            it.description = "Copies the git hooks from /git-hooks to the .git folder."
            it.group = "git hooks"
            it.from("${rootProject.rootDir}/scripts/pre-commit")
            it.into("${rootProject.rootDir}/.git/hooks")
            it.filter { line -> line.replace("{javaHome}", System.getProperty("java.home")) }
        }

        rootProject.tasks.register("installGitHooks", Exec::class.java) {
            it.description = "Installs the pre-commit git hooks from /git-hooks."
            it.group = "git hooks"
            it.workingDir = rootProject.rootDir
            it.commandLine = listOf("chmod")
            it.args("-R", "+x", ".git/hooks/")
            it.dependsOn("copyGitHooks")
            it.doLast { task ->
                task.logger.info("Git hook installed successfully.")
            }
        }

        rootProject.afterEvaluate {
            it.tasks
                .getByPath(":app:preBuild")
                .dependsOn(":installGitHooks")
        }
    }
}
