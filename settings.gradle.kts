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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// app module
include(":app")

// core modules
include(":core:design")
include(":core:locale:api")
include(":core:locale:impl")
include(":core:location")
include(":core:navigation")
include(":core:network")
include(":core:preferences:api")
include(":core:preferences:impl")
include(":core:threading")

// feature auth modules
include(":feature:auth:data:remote")
include(":feature:auth:navigation")
include(":feature:auth:ui")

// feature locator modules
include(":feature:locator:data:local")
include(":feature:locator:data:local-database")
include(":feature:locator:data:remote")
include(":feature:locator:data:repo")

// feature onboarding modules
include(":feature:onboarding:domain")
include(":feature:onboarding:navigation")
include(":feature:onboarding:ui")

// feature home modules
include(":feature:home:navigation")
include(":feature:home:ui")

// framework modules
include(":framework:hiltext:annotation")
include(":framework:hiltext:processor-binds")
include(":framework:hiltext:processor-room")
include(":framework:hiltext:util")
include(":framework:konsist")

// testing modules
include(":testing:extension")
include(":testing:instrument")
include(":testing:network")
include(":testing:util")
