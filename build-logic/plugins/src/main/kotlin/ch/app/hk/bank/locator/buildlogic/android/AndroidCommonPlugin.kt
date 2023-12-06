package ch.app.hk.bank.locator.buildlogic.android

import ch.app.hk.bank.locator.buildlogic.testing.AndroidTestPlugin
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidCommonPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        @Suppress("UnstableApiUsage")
        project
            .extensions
            .getByType(AndroidComponentsExtension::class.java)
            .finalizeDsl { extension ->
                configureVersion(extension = extension)
                configureBuiltTypes(extension = extension)
            }

        AndroidTestPlugin().apply(project)

        configKotlin(project = project)
    }

    private fun configureVersion(extension: CommonExtension<*, *, *, *, *>) {
        extension.compileSdk = 34
        extension.buildToolsVersion = "34.0.0"

        extension.defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        extension.compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    private fun configureBuiltTypes(extension: CommonExtension<*, *, *, *, *>) {
        extension.buildTypes {
            getByName("release") {
                it.isMinifyEnabled = false
                it.proguardFiles(
                    extension.getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
    }

    private fun configKotlin(project: Project) {
        project.tasks.withType<KotlinCompile>().configureEach {
            it.kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
}
