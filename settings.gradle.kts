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
include(":core:design:theme")
include(":core:design:ui")
include(":core:locale:api")
include(":core:locale:impl")
include(":core:location:api")
include(":core:location:impl")
include(":core:location:launcher")
include(":core:logging:api")
include(":core:logging:startup")
include(":core:map")
include(":core:navigation")
include(":core:network")
include(":core:preferences:api")
include(":core:preferences:impl")
include(":core:threading")

// feature auth modules
include(":feature:auth:data:remote")
include(":feature:auth:data:remote-firebase")
include(":feature:auth:data:repo")
include(":feature:auth:navigation")
include(":feature:auth:ui")

// feature bank modules
include(":feature:bank:data:local")
include(":feature:bank:data:local-database")
include(":feature:bank:data:remote")
include(":feature:bank:data:repo")
include(":feature:bank:ui")

// feature onboarding modules
include(":feature:onboarding:domain")
include(":feature:onboarding:navigation")
include(":feature:onboarding:ui")

// feature home modules
include(":feature:home:domain")
include(":feature:home:navigation")
include(":feature:home:ui")

// feature locator modules
include(":feature:locator:ui")

// library modules
include(":library:hiltwrap:annotation")
include(":library:hiltwrap:processor-binds")
include(":library:hiltwrap:processor-room")
include(":library:hiltwrap:util")

// lint modules
include(":lint:konsist")

// testing modules
include(":testing:extension")
include(":testing:google-play-services-tasks")
include(":testing:instrument")
include(":testing:network")
include(":testing:util")
