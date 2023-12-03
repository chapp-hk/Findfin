package ch.app.hk.bank.locator.buildlogic.ktlint

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

class KtlintPlugin : Plugin<Project> {
    private val ktlintGradlePluginId = "org.jlleitschuh.gradle.ktlint"
    private val ktlintVersion = "1.0.1"

    override fun apply(project: Project) {
        assertRootProject(rootProject = project)
        project.plugins.apply(ktlintGradlePluginId)
        configureSubProjects(rootProject = project)
        configureGitHook(rootProject = project)
    }

    private fun assertRootProject(rootProject: Project) {
        if (rootProject.rootProject !== rootProject) {
            throw GradleException(
                "The \"app-ktlint-plugin\" plugin cannot be applied to project '${rootProject.name}'" +
                        "because it is not the root project. Build file: ${rootProject.buildFile}"
            )
        }
    }

    private fun configureSubProjects(rootProject: Project) {
        rootProject.subprojects {
            it.apply(plugin = ktlintGradlePluginId)

            // configure plugin
            it.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
                version.set(ktlintVersion)
                debug.set(true)
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