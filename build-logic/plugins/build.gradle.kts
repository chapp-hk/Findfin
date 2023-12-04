import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

group = "ch.app.hk.bank.locator.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin.api)
    compileOnly(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(gradleKotlinDsl())
    implementation(libs.ktlint.gradle)
}

gradlePlugin {
    plugins {
        create("ktlint") {
            id = "app.plugin.ktlint"
            implementationClass = "$group.ktlint.KtlintPlugin"
        }

        create("android-common") {
            id = "app.plugin.android.common"
            implementationClass = "$group.android.AndroidCommonPlugin"
        }
    }
}