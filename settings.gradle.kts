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

// core modules
include(":core:design")
include(":core:network")
include(":core:threading")

// feature modules
include(":feature:locator:data:local")
include(":feature:locator:data:local-database")
include(":feature:locator:data:remote")

// framework modules
include(":framework:hiltext:annotation")
include(":framework:hiltext:processor-binds")
include(":framework:hiltext:util")

// testing modules
include(":testing:network")
include(":testing:util")
include(":framework:hiltext:processor-room")
