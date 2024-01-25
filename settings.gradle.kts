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
include(":core:locale")
include(":core:navigation")
include(":core:network")
include(":core:preferences:api")
include(":core:preferences:impl")
include(":core:threading")

// feature locator modules
include(":feature:locator:data:local")
include(":feature:locator:data:local-database")
include(":feature:locator:data:remote")
include(":feature:locator:data:repo")

// feature onboarding modules
include(":feature:onboarding:navigation")
include(":feature:onboarding:ui")

// framework modules
include(":framework:hiltext:annotation")
include(":framework:hiltext:processor-binds")
include(":framework:hiltext:processor-room")
include(":framework:hiltext:util")

// testing modules
include(":testing:extension")
include(":testing:network")
include(":testing:util")
include(":framework:hiltext:processor-room")
include(":feature:locator:data:repo")
include(":feature:onboarding:navigation")
