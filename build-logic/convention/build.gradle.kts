import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

group = "org.chapp.findfin.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin.api.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    implementation(gradleKotlinDsl())
    implementation(libs.ktlint.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
    implementation(libs.jacoco.core.plugin)
}

gradlePlugin {
    plugins {
        create("ktlint") {
            id = "app.plugin.ktlint"
            implementationClass = "$group.plugin.ktlint.KtlintPlugin"
        }

        create("detekt") {
            id = "app.plugin.detekt"
            implementationClass = "$group.plugin.detekt.DetektPlugin"
        }

        create("jacoco") {
            id = "app.plugin.jacoco"
            implementationClass = "$group.plugin.jacoco.JacocoRootCoveragePlugin"
        }

        create("jvm") {
            id = "app.plugin.jvm"
            implementationClass = "$group.plugin.jvm.JvmPlugin"
        }

        create("android-common") {
            id = "app.plugin.android.common"
            implementationClass = "$group.plugin.android.AndroidCommonPlugin"
        }

        create("compose") {
            id = "app.plugin.compose"
            implementationClass = "$group.plugin.compose.ComposePlugin"
        }

        create("hilt-android") {
            id = "app.plugin.hilt.android"
            implementationClass = "$group.plugin.hilt.HiltAndroidPlugin"
        }

        create("hilt-android-test") {
            id = "app.plugin.hilt.android.test"
            implementationClass = "$group.plugin.hilt.HiltAndroidTestPlugin"
        }

        create("hilt-jvm") {
            id = "app.plugin.hilt.jvm"
            implementationClass = "$group.plugin.hilt.HiltJvmPlugin"
        }

        create("room") {
            id = "app.plugin.room.android"
            implementationClass = "$group.plugin.room.RoomAndroidPlugin"
        }

        create("mapstruct") {
            id = "app.plugin.mapstruct"
            implementationClass = "$group.plugin.mapstruct.MapStructPlugin"
        }
    }
}
