pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HK-Bank-Locator"
include(":app")
include(":core:design")
include(":core:network")
include(":core:threading")
include(":feature:bank:data:remote")
include(":testing:network")
include(":testing:util")
